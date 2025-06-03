package org.sopt.category.service;

import lombok.RequiredArgsConstructor;
import org.sopt.category.domain.Category;
import org.sopt.category.domain.CategoryEntity;
import org.sopt.category.dto.request.CategoryCreateRequest;
import org.sopt.category.exception.CategoryCoreErrorCode;
import org.sopt.category.exception.CategoryDuplicatedException;
import org.sopt.category.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategorySaver categorySaver;

    @Transactional
    public Category createCategory(final Long userId, final CategoryCreateRequest categoryCreateRequest) {
        // 중복 체크
        if (categoryRepository.existsByNameAndColor(categoryCreateRequest.name(),
            categoryCreateRequest.color())) {
            throw new CategoryDuplicatedException(CategoryCoreErrorCode.DUPLICATED_CATEGORY);
        }

        CategoryEntity categoryEntity = CategoryEntity.builder()
            // 유저 주입
            .name(categoryCreateRequest.name())
            .color(categoryCreateRequest.color())
            .isVisible(true)
            .build();

        return categorySaver.save(categoryEntity);
    }

}


