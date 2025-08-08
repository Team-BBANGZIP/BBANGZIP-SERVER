package org.sopt.category.repository;

import org.sopt.category.domain.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    int countByUserId(Long userId);

    List<CategoryEntity> findAllByUserIdOrderByOrderAsc(Long userId);

}
