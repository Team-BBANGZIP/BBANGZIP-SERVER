package org.sopt.category.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.category.domain.Category;
import org.sopt.category.domain.CategoryEntity;
import org.sopt.category.repository.CategoryRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CategorySaver {
  private final CategoryRepository categoryRepository;

    @Transactional
    public Category save(final Category category) {
        CategoryEntity savedEntity = categoryRepository.save(CategoryEntity.forCreate(category));
        return savedEntity.toDomain();
    }
}
