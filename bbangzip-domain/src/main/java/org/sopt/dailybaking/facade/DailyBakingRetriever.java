package org.sopt.dailybaking.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.dailybaking.domain.DailyBakingEntity;
import org.sopt.dailybaking.repository.DailyBakingRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DailyBakingRetriever {

    private final DailyBakingRepository dailyBakingRepository;

    public Optional<DailyBakingEntity> findByUserIdAndBakeDateInDay(
            final long userId, final LocalDateTime startOfDay, final LocalDateTime endOfDay
    ) {
        return dailyBakingRepository.findByUserIdAndBakeDateInDay(userId, startOfDay, endOfDay);
    }
}