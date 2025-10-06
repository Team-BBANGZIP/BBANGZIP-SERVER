package org.sopt.jwt.core;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.exception.AuthErrorCode;
import org.sopt.exception.InvalidAuthHeaderException;
import org.sopt.exception.InvalidSecretKeyException;
import org.sopt.exception.TokenNotFoundException;
import org.sopt.jwt.auth.authentication.UserRole;
import org.sopt.jwt.auth.domain.type.AuthProvider;
import org.sopt.jwt.support.AuthConstants;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements InitializingBean {

    @Value("${jwt.secret-key}")
    private String secretKeyBase64;

    @Value("${jwt.access-token-expire-time}")
    private long ACCESS_TOKEN_EXPIRE_TIME;

    @Value("${jwt.refresh-token-expire-time}")
    @Getter
    private long REFRESH_TOKEN_EXPIRE_TIME;

    private SecretKey secretKey;

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKeyBase64);
        if (keyBytes.length < 64) {
            throw new InvalidSecretKeyException(AuthErrorCode.INVALID_SECRET_KEY);
        }
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(
            final long userId,
            final UserRole role,
            final org.sopt.jwt.auth.domain.type.AuthProvider provider,
            final String sessionId
    ) {
        final Instant now = Instant.now();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .subject(String.valueOf(userId))
                .claim(AuthConstants.USER_ID_CLAIM_NAME, userId)
                .claim(JwtClaimsKeys.ROLE, role.name())
                .claim(JwtClaimsKeys.PROVIDER, provider.name())
                .claim(JwtClaimsKeys.SESSIONID, sessionId)
                .claim(JwtClaimsKeys.TYPE, JwtClaimsKeys.ACCESS)
                .issuedAt(Date.from(now))
                .expiration(new Date(now.toEpochMilli() + ACCESS_TOKEN_EXPIRE_TIME))
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }

    public String generateRefreshToken(
            final long userId,
            final AuthProvider provider,
            final String sessionId
    ) {
        final Instant now = Instant.now();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .subject(String.valueOf(userId))
                .claim(AuthConstants.USER_ID_CLAIM_NAME, userId)
                .claim(JwtClaimsKeys.PROVIDER, provider.name())
                .claim(JwtClaimsKeys.SESSIONID, sessionId)
                .claim(JwtClaimsKeys.TYPE, JwtClaimsKeys.REFRESH)
                .issuedAt(Date.from(now))
                .expiration(new Date(now.toEpochMilli() + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }

    public Claims parseAndVerify(final String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .clockSkewSeconds(60)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getJwtFromRequest(final HttpServletRequest request) {
        final String bearerToken = request.getHeader(AuthConstants.AUTHORIZATION_HEADER);

        // 헤더 자체가 없으면 null 반환
        if (!StringUtils.hasText(bearerToken)) {
            return null;
        }

        // Bearer 접두사 없으면 invalid 헤더
        if (!bearerToken.startsWith(AuthConstants.BEARER_PREFIX)) {
            log.warn("Invalid Authorization header format: {}", bearerToken);
            throw new InvalidAuthHeaderException(AuthErrorCode.INVALID_AUTH_HEADER);
        }

        // 접두사 제거 후 토큰 추출
        final String token = bearerToken.substring(AuthConstants.BEARER_PREFIX.length());

        // 토큰 문자열이 비었으면 null 반환
        return StringUtils.hasText(token) ? token : null;
    }

    public String newSessionId() {
        return UUID.randomUUID().toString();
    }

}