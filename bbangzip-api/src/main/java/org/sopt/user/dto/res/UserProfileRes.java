package org.sopt.user.dto.res;

import org.sopt.user.domain.User;

public record UserProfileRes(
        String profileImageUrl,
        String nickname,
        String commitmentMessage
) {
    public static UserProfileRes from(User userProfile) {
        return new UserProfileRes(
                userProfile.getProfileImage(),
                userProfile.getNickname(),
                userProfile.getCommitmentMessage()
        );
    }
}
