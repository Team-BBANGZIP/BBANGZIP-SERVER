package org.sopt.category.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.category.dto.req.CategoryCreateReq;
import org.sopt.category.dto.req.CategoryReorderReq;
import org.sopt.category.dto.req.CategoryUpdateReq;
import org.sopt.category.dto.res.CategoryCreateRes;
import org.sopt.category.dto.res.CategoryRes;
import org.sopt.category.service.CategoryService;
import org.sopt.jwt.annotation.UserId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryCreateRes> createCategory(
            @UserId Long userId,
            @Valid @RequestBody final CategoryCreateReq categoryCreateReq
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryService.createCategory(userId, categoryCreateReq));
    }

    @GetMapping
    public ResponseEntity<List<CategoryRes>> getAllCategories(
            @UserId Long userId
    ) {
        return ResponseEntity.ok(categoryService.getAllCategories(userId));
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<CategoryRes> updateCategory(
            @UserId Long userId,
            @PathVariable final Long categoryId,
            @Valid @RequestBody final CategoryUpdateReq categoryUpdateReq
    ) {
        return ResponseEntity.ok(categoryService.updateCategory(userId, categoryId, categoryUpdateReq));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(
            @UserId Long userId,
            @PathVariable final Long categoryId
    ) {
        categoryService.deleteCategory(userId, categoryId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/order")
    public ResponseEntity<Void> reorderCategory(
            @UserId Long userId,
            @Valid @RequestBody final CategoryReorderReq categoryReorderReq
    ) {
        categoryService.reorderCategory(userId, categoryReorderReq.categoryOrder());
        return ResponseEntity.ok().build();
    }

}