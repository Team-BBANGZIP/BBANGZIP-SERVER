package org.sopt.category.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.category.domain.Category;
import org.sopt.category.domain.CategoryColor;
import org.sopt.category.domain.CategoryEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryFacade {

    private final CategoryRetriever categoryRetriever;
    private final CategorySaver categorySaver;
    private final CategoryRemover categoryRemover;
    private final CategoryUpdater categoryUpdater;

    // 유저의 카테고리 개수 반환
    public int getCategoryCountByUserId(final long userId) {
        return categoryRetriever.countByUserId(userId);
    }

    // 유저의 카테고리 목록 반환
    public List<Category> getCategoriesByUserId(final long userId) {
        return categoryRetriever.findAllByUserId(userId);
    }

    // 특정 카테고리 조회
    public Category getCategoryByIdAndUserId(final long categoryId, final long userId) {
        return categoryRetriever.findByIdAndUserId(categoryId, userId);
    }

    // 카테고리 저장
    public Category saveCategory(final Category category) {
        return categorySaver.save(category);
    }

    // 카테고리 수정
    public Category updateCategory(Long categoryId, Long userId, String newName, CategoryColor newColor, boolean newIsStopped) {
        return categoryUpdater.updateCategory(categoryId, userId, newName, newColor, newIsStopped);
    }

    // 카테고리 삭제
    public void deleteCategory(final Category category) {
        categoryRemover.delete(category);
    }

    // 카테고리 순서 변경
    public void updateCategoryOrder(final List<Category> existingCategories, final List<Long> newOrderIds, Long userId) {
        categoryUpdater.updateCategoryOrder(existingCategories, newOrderIds, userId);
    }

    public List<Category> getActiveCategoriesByUserId(Long userId) {
        return categoryRetriever.findActiveByUserId(userId)
                .stream()
                .map(CategoryEntity::toDomain)
                .toList();
    }

    /** 날짜별 투두 화면에 노출할 카테고리(멈춤 전 날짜까지 멈춘 카테고리 포함) */
    public List<Category> getCategoriesVisibleForTodoDate(long userId, LocalDate date) {
        return categoryRetriever.findVisibleForTodoList(userId, date);
    }

    public List<Long> getVisibleCategoryIdsForTodoDate(long userId, LocalDate date) {
        return categoryRetriever.findVisibleCategoryIdsForTodoDate(userId, date);
    }

    public void deleteAllByUserId(final Long userId) {
        categoryRemover.deleteAllByUserId(userId);
    }

}