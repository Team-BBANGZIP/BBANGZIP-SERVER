package org.sopt.user.dto.res;

import org.sopt.user.domain.User;
import org.sopt.user.type.DefaultProfileImage;

public record UserProfileUpdateRes(
        String profileImageUrl,
        Integer profileImageKey,
        String nickname,
        String commitmentMessage
) {
    public static UserProfileUpdateRes from(User userProfile) {
        return new UserProfileUpdateRes(
                userProfile.getProfileImage(),
                DefaultProfileImage.findKeyByUrl(userProfile.getProfileImage()).orElse(null),
                userProfile.getNickname(),
                userProfile.getCommitmentMessage()
        );
    }
}