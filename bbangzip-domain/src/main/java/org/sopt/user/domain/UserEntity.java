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
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class UserEntity extends BaseTimeEntity {

    @Id
    @Column(name = COLUMN_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = COLUMN_PLATFORM_USER_ID, nullable = false, unique = true)
    private Long platformUserId;

    @Column(name = COLUMN_PLATFORM, nullable = false)
    private String platform;

    @Column(name = NICKNAME)
    private String nickname;

    public UserEntity(Long platformUserId, String platform, String nickname) {
        this.platformUserId = platformUserId;
        this.platform = platform;
        this.nickname = nickname;
    }
}