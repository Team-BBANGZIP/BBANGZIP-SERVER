package org.sopt.category.service;

import lombok.RequiredArgsConstructor;
import org.sopt.category.domain.Category;
import org.sopt.category.domain.CategoryEntity;
import org.sopt.category.dto.req.CategoryCreateReq;
import org.sopt.category.dto.req.CategoryUpdateReq;
import org.sopt.category.dto.res.CategoryCreateRes;
import org.sopt.category.dto.res.CategoryRes;
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
    public CategoryCreateRes createCategory(final long userId, final CategoryCreateReq categoryCreateReq) {

        UserEntity user = userRetriever.findByUserId(userId);

        int categoryCount = categoryRetriever.countByUserId(userId);

        CategoryEntity categoryEntity = CategoryEntity.builder()
                .user(user)
                .name(categoryCreateReq.name())
                .color(categoryCreateReq.color())
                .isVisible(true)
                .order(categoryCount) // 최하단 순서 지정
                .build();

        Category saved = categorySaver.save(categoryEntity);
        return CategoryCreateRes.from(saved);

    }

    @Transactional(readOnly = true)
    public List<CategoryRes> getAllCategories(final long userId) {
        List<CategoryEntity> categories = categoryRetriever.findAllByUserId(userId);
        return categories.stream()
                .map(CategoryRes::from)
                .toList();
    }

    @Transactional
    public CategoryRes updateCategory(final long userId, final long categoryId, final CategoryUpdateReq categoryUpdateReq) {
        CategoryEntity category = categoryRetriever.findByIdAndUserId(categoryId, userId);
        // 순서는 유지, 나머지만 수정
        category.update(categoryUpdateReq.name(), categoryUpdateReq.color(), categoryUpdateReq.isVisible());
        return CategoryRes.from(category);
    }
}
