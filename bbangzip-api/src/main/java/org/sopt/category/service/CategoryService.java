package org.sopt.category.service;

import lombok.RequiredArgsConstructor;
import org.sopt.category.domain.Category;
import org.sopt.category.domain.CategoryEntity;
import org.sopt.category.dto.request.CategoryCreateRequest;
import org.sopt.category.facade.CategorySaver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategorySaver categorySaver;

    @Transactional
    public Category createCategory(final long userId, final CategoryCreateRequest categoryCreateRequest) {

        CategoryEntity categoryEntity = CategoryEntity.builder()
            .id(userId)
            .name(categoryCreateRequest.name())
            .color(categoryCreateRequest.color())
            .isVisible(true)
            .build();

        return categorySaver.save(categoryEntity);
    }

}


