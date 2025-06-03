package org.sopt.category.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.category.dto.request.CategoryCreateRequest;
import org.sopt.category.dto.response.CategoryCreateResponse;
import org.sopt.category.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryCreateResponse> createCategory(
        final Long userId,
        @Valid @RequestBody final CategoryCreateRequest categoryCreateRequest) {

        CategoryCreateResponse response = CategoryCreateResponse.from(
            categoryService.createCategory(userId, categoryCreateRequest)
        );

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response);
    }

}
