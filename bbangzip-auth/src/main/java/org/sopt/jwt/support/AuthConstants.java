package org.sopt.jwt.support;

public class AuthConstants {
    public static final String USER_ID_CLAIM_NAME = "uid";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String ANONYMOUS_USER = "anonymousUser";
    public static final String CHARACTER_TYPE = "utf-8";
    public static final String CONTENT_TYPE = "application/json";

    // AuthConstant
    public static final String[] AUTH_WHITE_LIST = {
            "/actuator/**",

            "/callback",
            "/test/jwt/token/issue",

            "/api/v1/auth/signin",
            "/api/v1/auth/re-issue",

            // OAuth2 Login 관련 엔드포인트
            "/oauth2/**",                  // Kakao, Apple redirect 등
            "/login/**",                   // /login/oauth2/code/{provider} 포함
            "/error",                      // 로그인 중 예외 발생 시
            "/favicon.ico"                 // 브라우저 요청용 (optional)
    };

    private AuthConstants() {
    }
}