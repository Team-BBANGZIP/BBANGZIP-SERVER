package org.sopt.user.dto.res;

import org.sopt.user.domain.User;

public record UserProfileUpdateRes(
        String profileImageUrl,
        String nickname,
        String commitmentMessage
) {
    public static UserProfileUpdateRes from(User userProfile) {
        return new UserProfileUpdateRes(
                userProfile.getProfileImage(),
                userProfile.getNickname(),
                userProfile.getCommitmentMessage()
        );
    }
}