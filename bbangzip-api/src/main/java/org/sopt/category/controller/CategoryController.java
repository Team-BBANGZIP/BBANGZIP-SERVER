package org.sopt.category.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.category.dto.req.CategoryCreateReq;
import org.sopt.category.dto.req.CategoryUpdateReq;
import org.sopt.category.dto.res.CategoryCreateRes;
import org.sopt.category.dto.res.CategoryRes;
import org.sopt.category.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            // TODO: 커스텀 어노테이션 final Long userId,
            @Valid @RequestBody final CategoryCreateReq categoryCreateReq
    ) {
        Long dummyUserId = 1L;

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryService.createCategory(dummyUserId, categoryCreateReq));
    }


    @GetMapping
    public ResponseEntity<List<CategoryRes>> getAllCategories(
            // TODO: 커스텀 어노테이션 final Long userId,
    ) {
        Long dummyUserId = 1L;
        return ResponseEntity.ok(categoryService.getAllCategories(dummyUserId));
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<CategoryRes> updateCategory(
            @PathVariable final Long categoryId,
            // TODO: 커스텀 어노테이션 final Long userId,
            @Valid @RequestBody final CategoryUpdateReq categoryUpdateReq
    ) {
        Long dummyUserId = 1L;
        return ResponseEntity.ok(categoryService.updateCategory(dummyUserId, categoryId, categoryUpdateReq));
    }


}
