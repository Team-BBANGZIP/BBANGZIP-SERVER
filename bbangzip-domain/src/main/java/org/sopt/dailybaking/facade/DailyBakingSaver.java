package org.sopt.dailybaking.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.dailybaking.domain.DailyBakingEntity;
import org.sopt.dailybaking.repository.DailyBakingRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DailyBakingSaver {

    private final DailyBakingRepository dailyBakingRepository;

    public DailyBakingEntity save(DailyBakingEntity entity) {
        return dailyBakingRepository.save(entity);
    }
}