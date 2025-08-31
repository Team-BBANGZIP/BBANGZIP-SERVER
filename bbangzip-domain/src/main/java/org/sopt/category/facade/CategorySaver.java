package org.sopt.category.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.category.domain.Category;
import org.sopt.category.domain.CategoryEntity;
import org.sopt.category.repository.CategoryRepository;
import org.sopt.user.domain.UserEntity;
import org.sopt.user.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CategorySaver {
  private final CategoryRepository categoryRepository;
  private final UserRepository userRepository;

    @Transactional
    public Category save(final Category category) {
        UserEntity userEntity = userRepository.getReferenceById(category.getUserId());
        CategoryEntity savedEntity = categoryRepository.save(CategoryEntity.forCreate(category, userEntity));
        return savedEntity.toDomain();
    }
}
