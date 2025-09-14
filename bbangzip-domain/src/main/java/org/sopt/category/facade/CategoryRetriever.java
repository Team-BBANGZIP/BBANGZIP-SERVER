package org.sopt.category.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.category.domain.Category;
import org.sopt.category.domain.CategoryEntity;
import org.sopt.category.exception.CategoryNotFoundException;
import org.sopt.category.repository.CategoryRepository;
import org.springframework.stereotype.Component;
import java.util.List;

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
}
