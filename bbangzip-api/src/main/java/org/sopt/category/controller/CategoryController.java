package org.sopt.category.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.category.dto.request.CategoryCreateRequest;
import org.sopt.category.dto.response.CategoryCreateResponse;
import org.sopt.category.dto.response.CategoryResponse;
import org.sopt.category.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<CategoryCreateResponse> createCategory(
            // TODO: 커스텀 어노테이션 final Long userId,
            @Valid @RequestBody final CategoryCreateRequest categoryCreateRequest
    ) {
        Long dummyUserId = 1L;

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryService.createCategory(dummyUserId, categoryCreateRequest));
    }


    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories(
            // TODO: 커스텀 어노테이션 final Long userId,
    ) {
        Long dummyUserId = 1L;
        return ResponseEntity.ok(categoryService.getAllCategories(dummyUserId));
    }


}
