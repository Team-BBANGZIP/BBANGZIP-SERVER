package org.sopt.timer.dto.res;

import lombok.Builder;

@Builder
public record TodayBakedCountRes(
        int todayBakedCount
) {
    public static TodayBakedCountRes of(int count) {
        return TodayBakedCountRes.builder()
                .todayBakedCount(count)
                .build();
    }
}