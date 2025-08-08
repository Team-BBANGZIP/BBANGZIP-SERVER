package org.sopt.category.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.category.dto.request.CategoryCreateRequest;
import org.sopt.category.dto.response.CategoryCreateResponse;
import org.sopt.category.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
