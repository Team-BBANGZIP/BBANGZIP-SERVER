package org.sopt.dailypostpone.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.user.domain.UserEntity;

import java.time.LocalDateTime;

import static org.sopt.dailypostpone.domain.DailyPostponeTableConstants.*;

@Entity
@Getter
@Table(name = TABLE_DAILY_POSTPONE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyPostponeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID)
    private Long id;

    @Column(name = COLUMN_POSTPONE_DATE, nullable = false)
    private LocalDateTime postponeDate;

    @Column(name = COLUMN_POSTPONE_COUNT)
    private int postponeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_USER_ID, nullable = false)
    private UserEntity user;
}
