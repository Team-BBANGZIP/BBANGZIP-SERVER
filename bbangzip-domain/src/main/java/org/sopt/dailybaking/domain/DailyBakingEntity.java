package org.sopt.dailybaking.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.common.BaseTimeEntity;
import org.sopt.user.domain.UserEntity;

import java.time.LocalDateTime;

import static org.sopt.dailybaking.domain.DailyBakingTableConstants.*;

@Entity
@Getter
@Table(name = TABLE_DAILY_BAKING)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyBakingEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_USER_ID, nullable = false)
    private UserEntity user;

    @Column(name = COLUMN_BAKE_DATE, nullable = false)
    private LocalDateTime bakeDate;

    @Column(name = COLUMN_BREAD_COUNT, nullable = false)
    private int breadCount;

}
