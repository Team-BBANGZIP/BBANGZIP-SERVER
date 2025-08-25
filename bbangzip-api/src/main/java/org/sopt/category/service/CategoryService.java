package org.sopt.category.service;

import lombok.RequiredArgsConstructor;
import org.sopt.category.domain.Category;
import org.sopt.category.dto.req.CategoryCreateReq;
import org.sopt.category.dto.req.CategoryUpdateReq;
import org.sopt.category.dto.res.CategoryCreateRes;
import org.sopt.category.dto.res.CategoryRes;
import org.sopt.category.facade.CategoryFacade;
import org.sopt.user.facade.UserFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryFacade categoryFacade;
    private final UserFacade userFacade;

    @Transactional
    public CategoryCreateRes createCategory(final long userId, final CategoryCreateReq categoryCreateReq) {
        userFacade.getUserById(userId);
        int categoryCount = categoryFacade.getCategoryCountByUserId(userId);
        Category category = Category.builder()
                .userId(userId)
                .name(categoryCreateReq.name())
                .color(categoryCreateReq.color())
                .isStopped(false)
                .order(categoryCount)  // 최하단 순서 지정
                .build();

        Category saved = categoryFacade.saveCategory(category);
        return CategoryCreateRes.from(saved);
    }

    @Transactional(readOnly = true)
    public List<CategoryRes> getAllCategories(final long userId) {
        return categoryFacade.getCategoriesByUserId(userId).stream()
                .map(CategoryRes::from)
                .toList();
    }

    @Transactional
    public CategoryRes updateCategory(final long userId, final long categoryId, final CategoryUpdateReq categoryUpdateReq) {

        Category category = categoryFacade.getCategoryByIdAndUserId(categoryId, userId);
        Category updatedCategory = category.update(
                categoryUpdateReq.name(),
                categoryUpdateReq.color() != null ? categoryUpdateReq.color() : category.getColor(),  // color가 null이면 기존 color 사용
                categoryUpdateReq.isStopped(),
                category.getOrder()  // order는 변경하지 않음
        );
        categoryFacade.saveCategory(updatedCategory);
        return CategoryRes.from(updatedCategory);
    }

    @Transactional
    public void deleteCategory(final long userId, final long categoryId) {
        Category category = categoryFacade.getCategoryByIdAndUserId(categoryId, userId);
        categoryFacade.deleteCategory(category);
    }
}