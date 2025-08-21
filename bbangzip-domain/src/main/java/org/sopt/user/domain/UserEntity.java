package org.sopt.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.common.BaseTimeEntity;

import static org.sopt.user.domain.UserTableConstants.*;

@Entity
@Getter
@Table(name = TABLE_USER)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseTimeEntity {

    @Id
    @Column(name = COLUMN_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = COLUMN_PLATFORM_USER_ID, nullable = false, unique = true)
    private Long platformUserId;

    @Column(name = COLUMN_PLATFORM, nullable = false)
    private String platform;

    @Column(name = COLUMN_NICKNAME, nullable = false)
    private String nickname;

    @Column(name = COLUMN_PROFILE_IMAGE)
    private String profileImage;

    @Column(name = COLUMN_COMMITMENT_MESSAGE)
    private String commitmentMessage;

    @Column(name = COLUMN_NOTIFICATION_ENABLED, nullable = false)
    private Boolean notificationEnabled;

    @Column(name = COLUMN_WEEK_START, nullable = false)
    private String weekStart;

    @Column(name = COLUMN_TOTAL_BREAD_COUNT, nullable = false)
    private int totalBreadCount;

    public void updateCommitmentMessage(String message) {
        this.commitmentMessage = message;
    }

    public User toDomain() {
        return User.builder()
                .id(id)
                .platformUserId(platformUserId)
                .platform(platform)
                .nickname(nickname)
                .profileImage(profileImage)
                .commitmentMessage(commitmentMessage)
                .notificationEnabled(notificationEnabled)
                .weekStart(weekStart)
                .totalBreadCount(totalBreadCount)
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }

}
