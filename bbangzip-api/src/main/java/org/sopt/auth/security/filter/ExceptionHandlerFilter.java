package org.sopt.auth.security.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.auth.exception.AuthErrorCode;
import org.sopt.auth.exception.BbangzipBusinessException;
import org.sopt.auth.security.logsupport.ErrorResponder;
import org.sopt.auth.security.logsupport.ErrorLogger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// 1번째로 구현
@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final ErrorLogger errorLogger;
    private final ErrorResponder errorResponder;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (AuthenticationException authEx) {
            // AuthenticationException이 발생하면, EntryPoint가 대신 처리하도록 요청 속성에 세팅 후 재던짐
            AuthErrorCode errorCode = mapExceptionToErrorCode(authEx);
            request.setAttribute("exception", errorCode);

            // Spring Security 흐름상 EntryPoint로 넘기기 위해 예외를 다시 던진다
            throw authEx;
        } catch (Exception e) {
            // 나머지 예외는 여기서 직접 JSON 응답 작성
            handleException(response, e);
        }
    }

    private void handleException(HttpServletResponse response, Exception e) {
       AuthErrorCode errorCode = mapExceptionToErrorCode(e);
        errorLogger.log(errorCode, e);
        errorResponder.write(response, errorCode);
    }

    private AuthErrorCode mapExceptionToErrorCode(Exception e) {
        if (e instanceof MalformedJwtException) return AuthErrorCode.MALFORMED_JWT_TOKEN;
        if (e instanceof IllegalArgumentException) return AuthErrorCode.TYPE_ERROR_JWT_TOKEN;
        if (e instanceof ExpiredJwtException) return AuthErrorCode.EXPIRED_JWT_TOKEN;
        if (e instanceof UnsupportedJwtException) return AuthErrorCode.UNSUPPORTED_JWT_TOKEN;
        if (e instanceof JwtException) return AuthErrorCode.UNKNOWN_JWT_TOKEN;
        if (e instanceof BbangzipBusinessException) return (AuthErrorCode) ((BbangzipBusinessException) e).getErrorCode();
        return AuthErrorCode.INTERNAL_SERVER_ERROR;
    }
}