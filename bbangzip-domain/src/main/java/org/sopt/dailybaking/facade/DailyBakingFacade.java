package org.sopt.dailybaking.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.dailybaking.domain.DailyBakingEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DailyBakingFacade {

    private final DailyBakingRetriever dailyBakingRetriever;
    private final DailyBakingSaver dailyBakingSaver;

    public Optional<DailyBakingEntity> findByUserIdAndBakeDateInDay(
            long userId, LocalDateTime startOfDay, LocalDateTime endOfDay
    ) {
        return dailyBakingRetriever.findByUserIdAndBakeDateInDay(userId, startOfDay, endOfDay);
    }

    public DailyBakingEntity save(DailyBakingEntity entity) {
        return dailyBakingSaver.save(entity);
    }
}