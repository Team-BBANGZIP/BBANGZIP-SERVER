package org.sopt.category.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.category.domain.Category;
import org.sopt.category.domain.CategoryEntity;
import org.sopt.category.exception.CategoryNotFoundException;
import org.sopt.category.repository.CategoryRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.sopt.category.exception.CategoryCoreErrorCode.CATEGORY_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class CategoryRetriever {

    private final CategoryRepository categoryRepository;

     // 유저의 카테고리 개수를 반환
    public int countByUserId(final long userId) {
        return categoryRepository.countByUserId(userId);
    }

    public List<Category> findAllByUserId(final long userId) {
        List<CategoryEntity> categoryEntities = categoryRepository.findAllByUserIdOrderByOrderAsc(userId);
        return categoryEntities.stream()
                .map(CategoryEntity::toDomain)
                .toList();
    }

    public Category findByIdAndUserId(final long categoryId, final long userId) {
        CategoryEntity categoryEntity = categoryRepository.findByIdAndUserId(categoryId, userId)
                .orElseThrow(() -> new CategoryNotFoundException(CATEGORY_NOT_FOUND));
        return categoryEntity.toDomain();
    }

    public List<CategoryEntity> findActiveByUserId(Long userId) {
        return categoryRepository.findActiveByUserId(userId);
    }

    /**
     * 멈춘 카테고리는 {@code stoppedDate}가 있는 경우, 그 날짜(로컬) 이전 캘린더 날짜에서만 투두 목록에 노출한다.
     * {@code stoppedDate}가 없는 레거시 멈춤 카테고리는 노출하지 않는다.
     */
    public List<Category> findVisibleForTodoList(long userId, LocalDate viewDate) {
        return categoryRepository.findAllByUserIdOrderByOrderAsc(userId).stream()
                .filter(entity -> isVisibleOnTodoCalendar(entity, viewDate))
                .map(CategoryEntity::toDomain)
                .toList();
    }

    public List<Long> findVisibleCategoryIdsForTodoDate(long userId, LocalDate viewDate) {
        return categoryRepository.findAllByUserIdOrderByOrderAsc(userId).stream()
                .filter(entity -> isVisibleOnTodoCalendar(entity, viewDate))
                .map(CategoryEntity::getId)
                .toList();
    }

    private static boolean isVisibleOnTodoCalendar(CategoryEntity category, LocalDate viewDate) {
        if (!category.isStopped()) {
            return true;
        }
        if (category.getStoppedDate() == null) {
            return false;
        }
        return viewDate.isBefore(category.getStoppedDate().toLocalDate());
    }

    public Optional<CategoryEntity> findById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }
}
