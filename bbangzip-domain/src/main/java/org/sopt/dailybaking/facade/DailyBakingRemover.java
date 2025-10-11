package org.sopt.dailybaking.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.dailybaking.repository.DailyBakingRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DailyBakingRemover {

    private final DailyBakingRepository dailyBakingRepository;

    public void deleteAllByUserId(Long userId) {
       dailyBakingRepository.deleteAllByUserId(userId);
    };

}