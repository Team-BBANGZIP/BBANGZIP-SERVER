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

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.sopt.category.exception.CategoryCoreErrorCode.CATEGORY_NOT_FOUND;
import static org.sopt.category.exception.CategoryCoreErrorCode.INVALID_CATEGORY_ORDER;
import static org.sopt.category.exception.CategoryCoreErrorCode.INVALID_CATEGORY_ORDER_DUPLICATE;
import static org.sopt.category.exception.CategoryCoreErrorCode.INVALID_CATEGORY_ORDER_MISMATCH;
import static org.sopt.category.exception.CategoryCoreErrorCode.INVALID_CATEGORY_ORDER_MISSING;

@Component
@RequiredArgsConstructor
public class CategoryUpdater {

    private final CategoryRepository categoryRepository;

    @Transactional
    public Category updateCategory(Long categoryId, Long userId, String newName, CategoryColor newColor, boolean newIsStopped) {
        CategoryEntity categoryEntity = categoryRepository.findByIdAndUserId(categoryId, userId)
                .orElseThrow(() -> new CategoryNotFoundException(CATEGORY_NOT_FOUND));

        // 이름이 변경된 경우에만 새 값을 전달
        String finalName = categoryEntity.getName().equals(newName) ? null : newName;
        CategoryColor finalColor = categoryEntity.getColor().equals(newColor) ? null : newColor;
        Boolean finalIsStopped = categoryEntity.isStopped() == newIsStopped ? null : newIsStopped;

        // 변경된 값만 전달하여 update 호출
        categoryEntity.update(
                finalName,
                finalColor,
                finalIsStopped,
                categoryEntity.getOrder() // 순서는 그대로 유지
        );

        return categoryEntity.toDomain();
    }



    @Transactional
    public void updateCategoryOrder(final List<Category> existingCategories, final List<Long> newOrderIds, Long userId) {

        // 개수와 ID 일치 여부 체크
        validateSameSize(existingCategories, newOrderIds);
        validateAllIdsMatch(existingCategories, newOrderIds);

        // ID → 새로운 인덱스 매핑
        Map<Long, Integer> idToOrder =
                IntStream.range(0, newOrderIds.size())
                        .boxed()
                        .collect(Collectors.toMap(newOrderIds::get, i -> i));

        List<CategoryEntity> categoryEntities = categoryRepository.findAllByIdAndUserId(newOrderIds, userId);

        for (CategoryEntity entity : categoryEntities) {
            Integer newOrder = idToOrder.get(entity.getId());
            if (newOrder != null && !newOrder.equals(entity.getOrder())) {
                entity.updateOrder(newOrder);
            }
        }
    }


    /**
     * 요청한 ID 개수와 실제 카테고리 개수가 다른 경우 예외
     */
    private void validateSameSize(List<Category> categories, List<Long> ids) {
        if (categories.size() != ids.size()) {
            throw new CategoryInvalidOrderException(INVALID_CATEGORY_ORDER_MISMATCH);
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
            Set<Long> missingIds = new HashSet<>(targetIds);
            missingIds.removeAll(existingIds);

            Set<Long> duplicateIds = new HashSet<>(existingIds);
            duplicateIds.removeAll(targetIds);

            if (!missingIds.isEmpty()) {
                throw new CategoryInvalidOrderException(INVALID_CATEGORY_ORDER_MISSING);
            }

            if (!duplicateIds.isEmpty()) {
                throw new CategoryInvalidOrderException(INVALID_CATEGORY_ORDER_DUPLICATE);
            }

            throw new CategoryInvalidOrderException(INVALID_CATEGORY_ORDER);
        }
    }
}
