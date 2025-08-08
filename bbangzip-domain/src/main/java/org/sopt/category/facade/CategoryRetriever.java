package org.sopt.category.facade;

import lombok.RequiredArgsConstructor;
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

    // 카테고리 ID로 조회
    public CategoryEntity findById(final long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(CATEGORY_NOT_FOUND));
    }

    public List<CategoryEntity> findAllByUserId(final long userId) {
        return categoryRepository.findAllByUserIdOrderByOrderAsc(userId);
    }
}
