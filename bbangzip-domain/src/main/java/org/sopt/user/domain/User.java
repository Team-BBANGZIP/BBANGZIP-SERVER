package org.sopt.user.domain;

import lombok.Builder;
import lombok.Getter;

import org.sopt.jwt.auth.authentication.UserRole;

import java.time.LocalDateTime;

@Getter
@Builder
public class User {

    private final Long id;
    private final UserRole userRole;
    private final Long platformUserId;
    private final String platform;
    private final String nickname;
    private final String profileImage;
    private final String commitmentMessage;
    private final Boolean notificationEnabled;
    private final String weekStart;
    private final int totalBreadCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;


    public static User fromEntity(final UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .userRole(userEntity.getUserRole())
                .platformUserId(userEntity.getPlatformUserId())
                .platform(userEntity.getPlatform())
                .nickname(userEntity.getNickname())
                .profileImage(userEntity.getProfileImage())
                .commitmentMessage(userEntity.getCommitmentMessage())
                .notificationEnabled(userEntity.getNotificationEnabled())
                .weekStart(userEntity.getWeekStart())
                .totalBreadCount(userEntity.getTotalBreadCount())
                .createdAt(userEntity.getCreatedAt())
                .updatedAt(userEntity.getUpdatedAt())
                .build();
    }
}