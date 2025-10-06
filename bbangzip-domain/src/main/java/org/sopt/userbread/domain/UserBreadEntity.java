package org.sopt.userbread.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.bread.domain.BreadEntity;
import org.sopt.common.BaseTimeEntity;
import org.sopt.user.domain.UserEntity;

import static org.sopt.userbread.domain.UserBreadTableConstants.*;

@Entity
@Getter
@Table(
        name = TABLE_USER_BREAD,
        uniqueConstraints = @UniqueConstraint(columnNames = { COLUMN_USER_ID, COLUMN_BREAD_ID })
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserBreadEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_USER_ID, nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_BREAD_ID, nullable = false)
    private BreadEntity bread;

    @Column(name = COLUMN_IS_UNLOCKED, nullable = false)
    private Boolean isUnlocked;

    public static UserBreadEntity create(UserEntity user, BreadEntity bread, boolean unlocked) {
        UserBreadEntity e = new UserBreadEntity();
        e.user = user;
        e.bread = bread;
        e.isUnlocked = unlocked;
        return e;
    }

}
