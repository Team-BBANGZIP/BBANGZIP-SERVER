package org.sopt.timer.service;

import lombok.RequiredArgsConstructor;
import org.sopt.bread.domain.BreadEntity;
import org.sopt.bread.facade.BreadFacade;
import org.sopt.dailybaking.domain.DailyBakingEntity;
import org.sopt.dailybaking.facade.DailyBakingFacade;
import org.sopt.timer.dto.req.TimerDoneReq;
import org.sopt.timer.dto.res.BreadListRes;
import org.sopt.timer.dto.res.TimerDoneRes;
import org.sopt.timer.dto.res.TodayBakedCountRes;
import org.sopt.user.domain.UserEntity;
import org.sopt.user.facade.UserFacade;
import org.sopt.userbread.domain.UserBreadEntity;
import org.sopt.userbread.facade.UserBreadFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimerService {

    private static final ZoneId ZONE_SEOUL = ZoneId.of("Asia/Seoul");

    private final UserFacade userFacade;
    private final DailyBakingFacade dailyBakingFacade;
    private final UserBreadFacade userBreadFacade;
    private final BreadFacade breadFacade;

    @Transactional
    public TimerDoneRes done(Long userId, TimerDoneReq req) {

        UserEntity user = userFacade.findByIdForUpdate(userId);

        TimeWindow today = getTodayWindow();

        DailyBakingEntity daily = dailyBakingFacade
                .findByUserIdAndBakeDateInDay(userId, today.start(), today.end())
                .map(entity -> {
                    entity.increaseCount(req.count());
                    return entity;
                })
                .orElseGet(() -> DailyBakingEntity.create(user, today.start(), req.count()));

        dailyBakingFacade.save(daily);
        user.increaseTotalBreadCount(req.count());

        return TimerDoneRes.of(req.count());
    }

    @Transactional(readOnly = true)
    public TodayBakedCountRes getTodayBakedCount(Long userId) {
        TimeWindow today = getTodayWindow();

        int count = dailyBakingFacade
                .findByUserIdAndBakeDateInDay(userId, today.start(), today.end())
                .map(DailyBakingEntity::getBreadCount)
                .orElse(0);

        return TodayBakedCountRes.of(count);
    }

    @Transactional(readOnly = true)
    public BreadListRes getBreadList(Long userId) {
        List<BreadEntity> breads = breadFacade.findAll();

        Map<Long, Boolean> unlockedMap = userBreadFacade.findAllByUserId(userId).stream()
                .collect(Collectors.toMap(
                        ub -> ub.getBread().getId(),
                        UserBreadEntity::getIsUnlocked,
                        (a, b) -> a
                ));

        List<BreadListRes.BreadSummaryRes> list = breads.stream()
                .map(b -> {
                    boolean isUnlocked = unlockedMap.getOrDefault(b.getId(), b.getRequiredCount() == 0);
                    return new BreadListRes.BreadSummaryRes(
                            b.getId(),
                            b.getName(),
                            isUnlocked,
                            b.getRequiredCount(),
                            b.getImageUrl()
                    );
                })
                .toList();

        return BreadListRes.of(list);
    }

    /**
     * 오늘의 시작/끝 시각 (KST 05:00 기준) 반환
     */
    private TimeWindow getTodayWindow() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZONE_SEOUL);

        LocalDate baseDate = (nowKst.getHour() < 5)
                ? nowKst.toLocalDate().minusDays(1)
                : nowKst.toLocalDate();

        ZonedDateTime startKst = baseDate.atTime(5, 0).atZone(ZONE_SEOUL);
        ZonedDateTime endKst = startKst.plusDays(1);

        return new TimeWindow(startKst.toLocalDateTime(), endKst.toLocalDateTime());
    }

    private record TimeWindow(LocalDateTime start, LocalDateTime end) {}

}