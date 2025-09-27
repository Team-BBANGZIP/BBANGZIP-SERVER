package org.sopt.user.dto.req;

public record UserProfileUpdateReq(
        int profileImageKey,
        String nickname,
        String commitmentMessage
) {}