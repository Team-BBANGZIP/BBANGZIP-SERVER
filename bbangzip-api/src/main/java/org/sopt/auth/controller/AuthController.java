package org.sopt.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.auth.dto.SignUpReq;
import org.sopt.auth.dto.SocialLoginReq;
import org.sopt.auth.dto.SocialLoginRes;
import org.sopt.auth.service.AuthService;
import org.sopt.jwt.annotation.UserId;
import org.sopt.jwt.auth.dto.ReissueTokensRes;
import org.sopt.jwt.core.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<SocialLoginRes> socialLogin(
            @RequestHeader("Provider-Token") String providerToken,
            @Valid @RequestBody SocialLoginReq req
    ) {
        SocialLoginRes res = authService.socialLogin(providerToken, req);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(
            @UserId Long userId,
            @Valid @RequestBody SignUpReq req
    ) {
        authService.signUp(userId, req);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/re-issue")
    public ResponseEntity<ReissueTokensRes> reissue(
            HttpServletRequest request
    ){
        String refreshToken = jwtTokenProvider.getJwtFromRequest(request);
        return ResponseEntity.ok(authService.reissue(refreshToken));
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(
            HttpServletRequest request
    ) {
        String accessToken = jwtTokenProvider.getJwtFromRequest(request);
        return ResponseEntity.ok(authService.logout(accessToken));
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity<Void> leave(
            @UserId Long userId,
            HttpServletRequest request
    ) {
        String accessToken = jwtTokenProvider.getJwtFromRequest(request);
        return ResponseEntity.ok(authService.leave(userId, accessToken));
    }

}