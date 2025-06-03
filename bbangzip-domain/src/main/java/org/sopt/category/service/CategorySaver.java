package org.sopt.category.service;

import lombok.RequiredArgsConstructor;
import org.sopt.category.domain.Category;
import org.sopt.category.domain.CategoryEntity;
import org.sopt.category.exception.CategoryCoreErrorCode;
import org.sopt.category.exception.CategoryDuplicatedException;
import org.sopt.category.repository.CategoryRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CategorySaver {
  private final CategoryRepository categoryRepository;

  @Transactional
  public Category save(final CategoryEntity categoryEntity) {
    try {
      final CategoryEntity savedEntity = categoryRepository.save(categoryEntity);
      return Category.fromEntity(savedEntity);
    } catch (DataIntegrityViolationException e) {
      throw new CategoryDuplicatedException(CategoryCoreErrorCode.DUPLICATED_CATEGORY);
    }
  }
}
