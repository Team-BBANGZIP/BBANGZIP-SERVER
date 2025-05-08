package org.sopt.user.domain;

import lombok.Getter;

@Getter
public class User {

    private final Long platformUserId;
    private final String platform;
    private final String nickname;

    public User(Long platformUserId, String platform, String nickname) {
        this.platformUserId = platformUserId;
        this.platform = platform;
        this.nickname = nickname;
    }

    public static User fromEntity(final UserEntity userEntity) {
        return new User(
                userEntity.getPlatformUserId(),
                userEntity.getPlatform(),
                userEntity.getNickname()

        );
    }
}