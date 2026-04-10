package org.sopt.user.domain;

import lombok.Builder;
import lombok.Getter;
import org.sopt.jwt.auth.authentication.UserRole;
import org.sopt.user.type.RegisterStatus;

import java.time.LocalDateTime;

@Getter
@Builder
public class User {

    private final Long id;
    private final UserRole userRole;
    private final String provider;
    private final String providerId;
    private final RegisterStatus registerStatus;
    private final Boolean isDeleted;
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
                .provider(userEntity.getProvider())
                .providerId(userEntity.getProviderId())
                .registerStatus(userEntity.getRegisterStatus())
                .isDeleted(userEntity.getIsDeleted())
                .nickname(userEntity.getNickname())
                .profileImage(userEntity.getProfileImage())
                .commitmentMessage(
                        userEntity.getCommitmentMessage() == null
                                ? UserEntity.DEFAULT_COMMITMENT_MESSAGE
                                : userEntity.getCommitmentMessage())
                .notificationEnabled(userEntity.getNotificationEnabled())
                .weekStart(userEntity.getWeekStart())
                .totalBreadCount(userEntity.getTotalBreadCount())
                .createdAt(userEntity.getCreatedAt())
                .updatedAt(userEntity.getUpdatedAt())
                .build();
    }
}