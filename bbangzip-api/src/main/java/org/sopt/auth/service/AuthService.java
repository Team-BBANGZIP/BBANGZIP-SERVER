package org.sopt.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.sopt.auth.dto.SignUpReq;
import org.sopt.auth.dto.SocialLoginReq;
import org.sopt.auth.dto.SocialLoginRes;
import org.sopt.auth.exception.AppleServerErrorException;
import org.sopt.auth.exception.AuthApiErrorCode;
import org.sopt.auth.exception.InvalidProviderException;
import org.sopt.auth.util.apple.ApplePublicKeyList;
import org.sopt.auth.util.kakao.KakaoConstant;
import org.sopt.auth.util.kakao.KakaoInfoClient;
import org.sopt.auth.util.kakao.KakaoUserInfoResponse;
import org.sopt.auth.util.apple.MyKeyLocator;
import org.sopt.category.facade.CategoryFacade;
import org.sopt.dailybaking.facade.DailyBakingFacade;
import org.sopt.exception.AuthErrorCode;
import org.sopt.exception.UnAuthorizedException;
import org.sopt.jwt.auth.authentication.UserRole;
import org.sopt.jwt.auth.domain.type.AuthProvider;
import org.sopt.todo.facade.TodoFacade;
import org.sopt.token.TokenService;
import org.sopt.jwt.auth.dto.ReissueTokensRes;
import org.sopt.user.domain.UserEntity;
import org.sopt.user.service.UserOnboardingService;
import org.sopt.user.type.RegisterStatus;
import org.sopt.auth.event.SignUpCompletedEvent;
import org.sopt.user.facade.UserFacade;
import org.sopt.userbread.facade.UserBreadFacade;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

import static org.sopt.jwt.auth.domain.type.AuthProvider.KAKAO;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenService tokenService;
    private final KakaoInfoClient kakaoInfoClient;

    private final CategoryFacade categoryFacade;
    private final DailyBakingFacade dailyBakingFacade;
    private final TodoFacade todoFacade;
    private final UserFacade userFacade;
    private final UserBreadFacade userBreadFacade;
    private final UserOnboardingService userOnboardingService;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * 소셜 로그인
     */
    public SocialLoginRes socialLogin(String providerToken, SocialLoginReq req) {
        if (providerToken == null || req.role() != UserRole.USER) {
            throw new UnAuthorizedException(AuthErrorCode.UNAUTHORIZED);
        }

        if (req.provider() == KAKAO) {
            return kakaoLogin(providerToken, req);
        }

        if (req.provider() == AuthProvider.APPLE) {
            return appleLogin(providerToken, req);
        }

        throw new InvalidProviderException(AuthApiErrorCode.INVALID_PROVIDER);
    }

    /**
     * APPLE 로그인
     */
    private SocialLoginRes appleLogin(String providerToken, SocialLoginReq req) {
        if (providerToken == null || providerToken.isEmpty()) {
            throw new UnAuthorizedException(AuthErrorCode.UNAUTHORIZED);
        }

        Claims claims;
        try {
            MyKeyLocator myKeyLocator = new MyKeyLocator(getPublicKeys()); // 캐시된 키 목록 사용
            claims = Jwts.parser()
                    .keyLocator(myKeyLocator)
                    .build()
                    .parseSignedClaims(providerToken) // Apple ID Token
                    .getPayload();
        } catch (Exception e) {
            // 애플 ID Token 검증 실패
            throw new AppleServerErrorException(AuthApiErrorCode.AUTH_APPLE_SERVER_ERROR);
        }

        String providerId = claims.getSubject();
        return findUserAndIssueToken(providerId, req);
    }

    //Apple 공개 키를 JWKS 엔드포인트에서 조회
    @Cacheable(value = "applePublicKeys", key = "'allKeys'") // key = "'allKeys'"를 사용하여 단일 캐시 엔트리로 관리
    public List<ApplePublicKeyList.ApplePublicKey> getPublicKeys() {
        try {
            ApplePublicKeyList response = WebClient.builder()
                    .baseUrl("https://appleid.apple.com")
                    .build()
                    .get()
                    .uri("/auth/keys")
                    .retrieve()
                    .bodyToMono(ApplePublicKeyList.class)
                    .block();

            // Apple JWKS 응답 비어 있는 경우
            if (response == null || response.keys() == null || response.keys().isEmpty()) {
                throw new AppleServerErrorException(AuthApiErrorCode.AUTH_APPLE_SERVER_ERROR);
            }

            return response.keys();
        } catch (Exception e) {
            // 공개 키 로드 실패
            throw new AppleServerErrorException(AuthApiErrorCode.AUTH_APPLE_SERVER_ERROR);
        }
    }

    private SocialLoginRes findUserAndIssueToken(String providerId, SocialLoginReq req) {
        UserEntity user;
        Optional<UserEntity> optionalUser = userFacade.getByProviderAndProviderId(String.valueOf(req.provider()), providerId);

        // 이미 유저가 존재하는 경우
        if(optionalUser.isPresent()) {
            user = optionalUser.get();

            if(optionalUser.get().getIsDeleted()) {
                // 탈퇴한 회원인 경우 다시 회원 자격 복구
                user.revertDeleteUser();
            }

            // 이미 가입된 유저 토큰 재발급(= 초기 유저와 동일한 로직)
            return tokenService.issueToken(req, user.getId(), user.getRegisterStatus());

        } else {
            user = UserEntity.builder()
                    .provider(String.valueOf(req.provider()))
                    .providerId(providerId)
                    .registerStatus(RegisterStatus.SOCIAL_LOGIN_COMPLETED)
                    .userRole(UserRole.USER)
                    .build();

            UserEntity newUser = userFacade.save(user);
            return tokenService.issueToken(req, newUser.getId(), RegisterStatus.SOCIAL_LOGIN_COMPLETED);
        }
    }

    /**
     * KAKAO 로그인
     */
    @Transactional
    public SocialLoginRes kakaoLogin(final String accessToken, final SocialLoginReq req) {
        if (accessToken == null || accessToken.isBlank()) {
            throw new UnAuthorizedException(AuthErrorCode.UNAUTHORIZED);
        }

        KakaoUserInfoResponse info = getUserInfo(accessToken);
        String providerId = String.valueOf(info.id());

        return findUserAndIssueToken(providerId, req);
    }

    public KakaoUserInfoResponse getUserInfo(
            final String accessToken
    ){
        return kakaoInfoClient.kakaoInfo(
                KakaoConstant.BEARER + accessToken
        );
    }

    /**
     * 회원가입 API
     */
    @Transactional
    public void signUp(final long userId, final SignUpReq req) {
       userFacade.updateProfile(userId, req.profileImageKey(), req.nickname(), null);
       userFacade.updateRegisterStatus(userId, RegisterStatus.PROFILE_COMPLETED);

       userBreadFacade.unlockSaltBreadOnSignUp(userId);
       userOnboardingService.createDefaultsFor(userId);

       eventPublisher.publishEvent(new SignUpCompletedEvent(userId, req.nickname()));
    }

    @Transactional
    public ReissueTokensRes reissue(final String refreshToken) {
        return tokenService.reissue(refreshToken);
    }

    public Void logout(String accessToken) {
        tokenService.logout(accessToken);
        return null;
    }


    @Transactional
    public Void leave(Long userId, String accessToken) {

        tokenService.leave(accessToken);

        todoFacade.deleteAllByUserId(userId);
        categoryFacade.deleteAllByUserId(userId);

        dailyBakingFacade.deleteAllByUserId(userId);
        userBreadFacade.deleteAllByUserId(userId);

        userFacade.deleteByUserId(userId);

        return null;
    }
}
