package org.sopt.category.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.category.domain.Category;
import org.sopt.category.domain.CategoryColor;
import org.sopt.category.domain.CategoryEntity;
import org.sopt.category.exception.CategoryInvalidOrderException;
import org.sopt.category.exception.CategoryNotFoundException;
import org.sopt.category.repository.CategoryRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.sopt.category.exception.CategoryCoreErrorCode.CATEGORY_NOT_FOUND;
import static org.sopt.category.exception.CategoryCoreErrorCode.INVALID_CATEGORY_ORDER;

@Component
@RequiredArgsConstructor
public class CategoryUpdater {

    private final CategoryRepository categoryRepository;

    @Transactional
    public Category updateCategory(Long categoryId, Long userId, String newName, CategoryColor newColor, boolean newIsStopped) {
        CategoryEntity categoryEntity = categoryRepository.findByIdAndUserId(categoryId, userId)
                .orElseThrow(() -> new CategoryNotFoundException(CATEGORY_NOT_FOUND));

        categoryEntity.update(
                newName,
                newColor != null ? newColor : categoryEntity.getColor(),
                newIsStopped,
                categoryEntity.getOrder() // 순서는 그대로 유지
        );

        return categoryEntity.toDomain();
    }



    @Transactional
    public void updateCategoryOrder(final List<Category> existingCategories, final List<Long> newOrderIds) {

        // 개수와 ID 일치 여부 체크
        validateSameSize(existingCategories, newOrderIds);
        validateAllIdsMatch(existingCategories, newOrderIds);

        // ID → 새로운 인덱스 매핑
        Map<Long, Integer> idToOrder = new HashMap<>();
        for (int i = 0; i < newOrderIds.size(); i++) {
            idToOrder.put(newOrderIds.get(i), i);
        }

        List<CategoryEntity> categoryEntities = categoryRepository.findAllById(newOrderIds);

        for (CategoryEntity entity : categoryEntities) {
            Integer newOrder = idToOrder.get(entity.getId());
            if (newOrder != null) {
                entity.update(
                        entity.getName(),
                        entity.getColor(),
                        entity.isStopped(),
                        newOrder // 새로운 정렬 순서
                );
            }
        }

    }


    /**
     * 요청한 ID 개수와 실제 카테고리 개수가 다른 경우 예외
     */
    private void validateSameSize(List<Category> categories, List<Long> ids) {
        if (categories.size() != ids.size()) {
            throw new CategoryInvalidOrderException(INVALID_CATEGORY_ORDER);
        }
    }

    /**
     * 요청한 ID가 기존 ID와 정확히 일치하는지 검증
     * → 누락, 중복, 잘못된 ID 방지
     */
    private void validateAllIdsMatch(List<Category> categories, List<Long> ids) {
        Set<Long> existingIds = categories.stream()
                .map(Category::getId)
                .collect(Collectors.toSet());

        Set<Long> targetIds = new HashSet<>(ids);

        if (!existingIds.equals(targetIds)) {
            throw new CategoryInvalidOrderException(INVALID_CATEGORY_ORDER);
        }
    }
}
