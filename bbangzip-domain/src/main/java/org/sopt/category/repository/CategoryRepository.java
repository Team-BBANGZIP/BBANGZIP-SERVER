package org.sopt.category.repository;

import org.sopt.category.domain.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    int countByUserId(Long userId);

    List<CategoryEntity> findAllByUserIdOrderByOrderAsc(Long userId);

    Optional<CategoryEntity> findByIdAndUserId(Long id, Long userId);
}
