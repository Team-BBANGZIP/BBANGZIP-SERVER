package org.sopt.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.sopt.auth.service.AuthService;
import org.sopt.jwt.auth.dto.ReissueTokensRes;
import org.sopt.jwt.core.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/re-issue")
    public ResponseEntity<ReissueTokensRes> reissue(
            HttpServletRequest request
    ){
        String refreshToken = jwtTokenProvider.getJwtFromRequest(request);
        return ResponseEntity.ok(authService.reissue(refreshToken));
    }

}