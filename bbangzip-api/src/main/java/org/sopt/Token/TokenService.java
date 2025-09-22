package org.sopt.Token;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.sopt.exception.AuthErrorCode;
import org.sopt.exception.InvalidTokenException;
import org.sopt.exception.TokenNotFoundException;
import org.sopt.jwt.auth.authentication.UserRole;
import org.sopt.jwt.auth.domain.Token;
import org.sopt.jwt.auth.domain.TokenRepository;
import org.sopt.jwt.auth.domain.type.AuthProvider;
import org.sopt.jwt.auth.dto.ReissueTokensRes;
import org.sopt.jwt.core.JwtClaimsKeys;
import org.sopt.jwt.core.JwtTokenProvider;
import org.sopt.jwt.core.TokenHasher;
import org.sopt.jwt.core.TokenId;
import org.sopt.jwt.support.AuthConstants;
import org.sopt.user.facade.UserFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRepository tokenRepository;
    private final TokenHasher tokenHasher;
    private final UserFacade userFacade;

    @Transactional
    public ReissueTokensRes reissue(String refreshToken) {

        final Claims claims = jwtTokenProvider.parseAndVerify(refreshToken);

        final String type = claims.get(JwtClaimsKeys.TYPE, String.class);
        if (!JwtClaimsKeys.REFRESH.equals(type)) {
            throw new InvalidTokenException(AuthErrorCode.TYPE_ERROR_JWT_TOKEN);
        }

        Long userId = claims.get(AuthConstants.USER_ID_CLAIM_NAME, Long.class);
        String sessionId = claims.get(JwtClaimsKeys.SESSIONID, String.class);
        AuthProvider provider = AuthProvider.valueOf(claims.get(JwtClaimsKeys.PROVIDER, String.class));
        UserRole role = userFacade.getUserById(userId).getUserRole();

        String tokenId = new TokenId(userId, sessionId).toString();
        Token stored = tokenRepository.findById(tokenId)
                .orElseThrow(() -> new TokenNotFoundException(AuthErrorCode.REFRESH_TOKEN_NOT_FOUND_IN_STORE));

        String providedHash = tokenHasher.hash(refreshToken);
        if (!providedHash.equals(stored.getRefreshTokenHash())) {
            throw new InvalidTokenException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }

        String newSessionId = jwtTokenProvider.newSessionId();
        String newAT = jwtTokenProvider.generateAccessToken(userId, role, provider, newSessionId);
        String newRT = jwtTokenProvider.generateRefreshToken(userId, provider, newSessionId);

        tokenRepository.deleteById(tokenId);
        Token newToken = Token.builder()
                .id(new TokenId(userId, newSessionId).toString())
                .userId(userId)
                .authProvider(provider)
                .refreshTokenHash(tokenHasher.hash(newRT))
                .deviceName(stored.getDeviceName())
                .deviceType(stored.getDeviceType())
                .osType(stored.getOsType())
                .osVersion(stored.getOsVersion())
                .appVersion(stored.getAppVersion())
                .issuedAt(Instant.now())
                .lastUsedAt(Instant.now())
                .build();
        tokenRepository.save(newToken);

        return ReissueTokensRes.builder()
                .accessToken(newAT)
                .refreshToken(newRT)
                .build();
    }

}