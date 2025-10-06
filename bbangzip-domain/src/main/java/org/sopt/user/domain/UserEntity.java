package org.sopt.user.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.sopt.common.BaseTimeEntity;
import org.sopt.jwt.auth.authentication.UserRole;
import org.sopt.user.type.RegisterStatus;
import org.sopt.user.type.RegisterStatusConverter;

import static org.sopt.user.domain.UserTableConstants.*;

@Builder
@Entity
@Getter
@Table(name = TABLE_USER)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserEntity extends BaseTimeEntity {

    @Id
    @Column(name = COLUMN_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = COLUMN_USER_ROLE)
    private UserRole userRole;

    @Column(name = COLUMN_PROVIDER, nullable = false, length = 20)
    private String provider; // KAKAO | APPLE

    @Column(name = COLUMN_PROVIDER_ID, nullable = false, length = 255)
    private String providerId; // 소셜 프로바이더 유저 고유 ID

    @Builder.Default
    @Convert(converter = RegisterStatusConverter.class)
    @Column(name = COLUMN_REGISTER_STATUS, nullable = false)
    private RegisterStatus registerStatus = RegisterStatus.SOCIAL_LOGIN_COMPLETED;

    @Builder.Default
    @Column(name = COLUMN_IS_DELETED, nullable = false)
    @ColumnDefault("false")
    private Boolean isDeleted = false;

    @Column(name = COLUMN_NICKNAME)
    private String nickname;

    @Column(name = COLUMN_PROFILE_IMAGE)
    private String profileImage;

    @Column(name = COLUMN_COMMITMENT_MESSAGE)
    private String commitmentMessage;

    @Builder.Default
    @Column(name = COLUMN_NOTIFICATION_ENABLED, nullable = false)
    private Boolean notificationEnabled = true;

    @Builder.Default
    @Column(name = COLUMN_WEEK_START, nullable = false)
    private String weekStart = "mon";

    @Builder.Default
    @Column(name = COLUMN_TOTAL_BREAD_COUNT, nullable = false)
    private int totalBreadCount = 0;

    public void updateCommitmentMessage(String message) {
        this.commitmentMessage = message;
    }

    public void updateProfile(String nickname, String profileImage, String commitmentMessage) {
        if (nickname != null) this.nickname = nickname;
        if (profileImage != null) this.profileImage = profileImage;
        if (commitmentMessage != null) this.commitmentMessage = commitmentMessage;
    }

    public User toDomain() {
        return User.builder()
                .id(id)
                .userRole(userRole)
                .provider(provider)
                .providerId(providerId)
                .registerStatus(registerStatus)
                .isDeleted(false)
                .nickname(nickname)
                .profileImage(profileImage)
                .commitmentMessage(commitmentMessage)
                .notificationEnabled(true)
                .weekStart("mon")
                .totalBreadCount(0)
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }

    public void increaseTotalBreadCount(int delta) {
        this.totalBreadCount += delta;
    }

    public void revertDeleteUser() {
        this.isDeleted = false;
    }

    public void updateRegisterStatus(RegisterStatus status) {
        this.registerStatus = status;
    }
}