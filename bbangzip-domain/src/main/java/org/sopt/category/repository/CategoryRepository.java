package org.sopt.category.repository;

import org.sopt.category.domain.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    int countByUserId(Long userId);

    List<CategoryEntity> findAllByUserIdOrderByOrderAsc(Long userId);

    Optional<CategoryEntity> findByIdAndUserId(Long id, Long userId);

    @Query("SELECT c FROM CategoryEntity c WHERE c.id IN :ids AND c.user.id = :userId")
    List<CategoryEntity> findAllByIdAndUserId(@Param("ids") List<Long> ids, @Param("userId") Long userId);

    @Query("""
    SELECT c FROM CategoryEntity c
    WHERE c.user.id = :userId
      AND c.isStopped = false
    ORDER BY c.order ASC
""")
    List<CategoryEntity> findActiveByUserId(Long userId);

    void deleteAllByUserId(Long userId);
}
