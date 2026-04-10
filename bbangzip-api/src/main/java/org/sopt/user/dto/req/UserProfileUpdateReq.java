package org.sopt.user.dto.req;

import jakarta.validation.constraints.Size;

public record UserProfileUpdateReq(
        int profileImageKey,
        String nickname,
        @Size(max = 50, message = "다짐 메시지는 공백 포함 50자 이하여야 합니다.")
        String commitmentMessage
) {}
