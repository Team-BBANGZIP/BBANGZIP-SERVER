package org.sopt.auth.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.sopt.auth.exception.AuthConstant;
import org.sopt.auth.exception.AuthErrorCode;
import org.sopt.code.ErrorCode;
import org.sopt.response.BaseResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /*
    필터에서 인증이나 인가 통과하지 못했을 떄, Handle 할 클래스
    인증 실패 시, Unauthorized 에러를 리턴할 EntryPoint
    */

    private final ObjectMapper objectMapper;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {

        ErrorCode errorCode = (ErrorCode) request.getAttribute("exception");

        // 만약 에러 코드가 없으면 기본값으로 처리
        if (errorCode == null) {
            errorCode = AuthErrorCode.WRONG_ENTRY_POINT;
        }

        // JSON 형태로 응답 내려주기
        response.setContentType(AuthConstant.CONTENT_TYPE);
        response.setCharacterEncoding(AuthConstant.CHARACTER_TYPE);
        response.setStatus(errorCode.getHttpStatus().value());
        response.getWriter().write(
                objectMapper.writeValueAsString(BaseResponse.fail(errorCode))
        );
    }
}

