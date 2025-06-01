package org.sopt.jwt.dto;

import lombok.Builder;

@Builder
public record ReissueJwtTokensDto(
        String accessToken,
        String refreshToken
) {
}
