package org.sopt.auth.service;

import lombok.RequiredArgsConstructor;
import org.sopt.token.TokenService;
import org.sopt.jwt.auth.dto.ReissueTokensRes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenService tokenService;

    @Transactional
    public ReissueTokensRes reissue(final String refreshToken) {
        return tokenService.reissue(refreshToken);
    }

}