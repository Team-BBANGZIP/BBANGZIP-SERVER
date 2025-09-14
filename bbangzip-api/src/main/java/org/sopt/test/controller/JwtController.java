package org.sopt.test.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.jwt.annotation.UserId;
import org.sopt.jwt.auth.domain.Token;
import org.sopt.jwt.auth.domain.TokenRepository;
import org.sopt.jwt.core.JwtTokenProvider;
import org.sopt.jwt.core.TokenHasher;
import org.sopt.test.dto.jwt.IssueTokenRequest;
import org.sopt.test.dto.jwt.JwtTokensDto;
import org.sopt.test.dto.jwt.TestDto;
import org.sopt.test.dto.jwt.TestSecurity;
import org.sopt.user.domain.User;
import org.sopt.user.facade.UserFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/jwt")
@RequiredArgsConstructor
@Slf4j
public class JwtController {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRepository tokenRepository;
    private final UserFacade userFacade;
    private final TokenHasher tokenHasher;

    @PostMapping("/token/issue")
    public ResponseEntity<JwtTokensDto> issueToken(
            @Valid @RequestBody IssueTokenRequest req
    ) {
        // userId에 해당하는 유저가 실제로 db에 있는지 찾기
        User user = userFacade.getUserById(req.userId());

        final String sessionId = jwtTokenProvider.newSessionId();
        log.info("발급된 SessionId = {}", sessionId);

        final String accessToken  = jwtTokenProvider.generateAccessToken(
                req.userId(), req.role(), req.provider(), sessionId
        );
        final String refreshToken = jwtTokenProvider.generateRefreshToken(
                req.userId(), req.provider(), sessionId
        );

        Token token = Token.builder()
                .id(new org.sopt.jwt.core.TokenId(user.getId(), sessionId).toString())
                .userId(user.getId())
                .authProvider(req.provider())
                .refreshTokenHash(tokenHasher.hash(refreshToken))
                .deviceName(req.deviceName())
                .deviceType(req.deviceType())
                .osType(req.osType())
                .osVersion(req.osVersion())
                .appVersion(req.appVersion())
                .issuedAt(java.time.Instant.now())
                .lastUsedAt(java.time.Instant.now())
                .build();

        tokenRepository.save(token);

        JwtTokensDto tokens = JwtTokensDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        return ResponseEntity.ok(tokens);
    }

    /**
     * content + @UserId 사용 테스트
     */
    @PostMapping("/security")
    public ResponseEntity<TestDto> testSecurity(
            @UserId Long userId,
            @Valid @RequestBody TestSecurity body
    ) {
        return ResponseEntity.ok(TestDto.builder()
                .content(body.name() + " " + userId)
                .build());
    }

}