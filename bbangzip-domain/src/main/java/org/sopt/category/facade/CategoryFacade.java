package org.sopt.category.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.category.domain.Category;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryFacade {

    private final CategoryRetriever categoryRetriever;
    private final CategorySaver categorySaver;
    private final CategoryRemover categoryRemover;

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

    // 카테고리 삭제
    public void deleteCategory(final Category category) {
        categoryRemover.delete(category);
    }
}
