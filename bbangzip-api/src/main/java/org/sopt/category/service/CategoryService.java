package org.sopt.category.service;

import lombok.RequiredArgsConstructor;
import org.sopt.category.domain.Category;
import org.sopt.category.domain.CategoryEntity;
import org.sopt.category.dto.request.CategoryCreateRequest;
import org.sopt.category.dto.request.CategoryUpdateRequest;
import org.sopt.category.dto.response.CategoryCreateResponse;
import org.sopt.category.dto.response.CategoryResponse;
import org.sopt.category.facade.CategoryRetriever;
import org.sopt.category.facade.CategorySaver;
import org.sopt.user.domain.UserEntity;
import org.sopt.user.facade.UserRetriever;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategorySaver categorySaver;
    private final CategoryRetriever categoryRetriever;

    private final UserRetriever userRetriever;

    @Transactional
    public CategoryCreateResponse createCategory(final long userId, final CategoryCreateRequest categoryCreateRequest) {

        UserEntity user = userRetriever.findByUserId(userId);

        int categoryCount = categoryRetriever.countByUserId(userId);

        CategoryEntity categoryEntity = CategoryEntity.builder()
             .user(user)
            .name(categoryCreateRequest.name())
            .color(categoryCreateRequest.color())
            .isVisible(true)
            .order(categoryCount) // 최하단 순서 지정
            .build();

        Category saved = categorySaver.save(categoryEntity);
        return CategoryCreateResponse.from(saved);

    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories(final long userId) {
        List<CategoryEntity> categories = categoryRetriever.findAllByUserId(userId);
        return categories.stream()
                .map(CategoryResponse::from)
                .toList();
    }

    @Transactional
    public CategoryResponse updateCategory(final long userId, final long categoryId, final CategoryUpdateRequest categoryUpdateRequest) {
        CategoryEntity category = categoryRetriever.findByIdAndUserId(categoryId, userId);
        // 순서는 유지, 나머지만 수정
        category.update(categoryUpdateRequest.name(), categoryUpdateRequest.color(), categoryUpdateRequest.isVisible());
        return CategoryResponse.from(category);
    }


}


