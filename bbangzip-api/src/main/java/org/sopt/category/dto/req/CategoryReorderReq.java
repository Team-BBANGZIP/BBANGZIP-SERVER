package org.sopt.category.dto.req;

import jakarta.validation.constraints.NotEmpty;
import org.sopt.common.validation.PositiveIdList;

import java.util.List;

public record CategoryReorderReq(
        @NotEmpty(message = "카테고리 순서 리스트는 비어 있을 수 없습니다.")
        @PositiveIdList
        List<Long> categoryOrder
) {}