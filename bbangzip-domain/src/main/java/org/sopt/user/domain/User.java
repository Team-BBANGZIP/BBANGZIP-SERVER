package org.sopt.user.domain;

import lombok.Getter;

@Getter
public class User {

    private final Long platformUserId;
    private final String platform;
    private final String nickname;
    private final String commitmentMessage;

    public User(Long platformUserId, String platform, String nickname, String commitmentMessage) {
        this.platformUserId = platformUserId;
        this.platform = platform;
        this.nickname = nickname;
        this.commitmentMessage = commitmentMessage;
    }

    public static User fromEntity(final UserEntity userEntity) {
        return new User(
                userEntity.getPlatformUserId(),
                userEntity.getPlatform(),
                userEntity.getNickname(),
                userEntity.getCommitmentMessage()
        );
    }
}