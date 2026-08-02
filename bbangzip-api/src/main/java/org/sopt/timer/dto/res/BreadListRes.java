package org.sopt.timer.dto.res;

import java.util.List;

public record BreadListRes(
        int totalCount,
        List<BreadSummaryRes> breadList
) {
    public static BreadListRes of(int totalCount, List<BreadSummaryRes> list) {
        return new BreadListRes(totalCount, list);
    }

    public record BreadSummaryRes(
            Long breadId,
            String breadName,
            boolean isUnlocked,
            int requiredCount,
            String imageUrl
    ) {}
}