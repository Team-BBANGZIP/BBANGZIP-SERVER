package org.sopt.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignUpReq(
        @NotBlank(message = "닉네임은 필수로 입력해야 합니다.")
        String nickname,

        @NotNull(message = "프로필 이미지 키는 필수로 입력해야 합니다.")
        Integer profileImageKey
) {}