package org.sopt.timer.service;

import lombok.RequiredArgsConstructor;
import org.sopt.dailybaking.domain.DailyBakingEntity;
import org.sopt.dailybaking.facade.DailyBakingFacade;
import org.sopt.timer.dto.req.TimerDoneReq;
import org.sopt.timer.dto.res.TimerDoneRes;
import org.sopt.user.domain.UserEntity;
import org.sopt.user.facade.UserFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class TimerService {

    private static final ZoneId ZONE_SEOUL = ZoneId.of("Asia/Seoul");

    private final UserFacade userFacade;
    private final DailyBakingFacade dailyBakingFacade;

    @Transactional
    public TimerDoneRes done(Long userId, TimerDoneReq req) {

        UserEntity user = userFacade.findByIdForUpdate(userId);

        LocalDateTime startOfDay = req.targetDate().atStartOfDay(ZONE_SEOUL).toLocalDateTime();
        LocalDateTime endOfDay   = startOfDay.plusDays(1);

        DailyBakingEntity daily = dailyBakingFacade
                .findByUserIdAndBakeDateInDay(userId, startOfDay, endOfDay)
                .map(e -> { e.increaseCount(req.count()); return e; })
                .orElseGet(() -> DailyBakingEntity.create(user, startOfDay, req.count()));

        dailyBakingFacade.save(daily);

        user.increaseTotalBreadCount(req.count());

        return TimerDoneRes.of(req.count());
    }
}