package org.sopt.auth.jwt.dto;

import lombok.Builder;

@Builder
public record JwtTokensDto(
        String accessToken,
        String refreshToken
) { }

