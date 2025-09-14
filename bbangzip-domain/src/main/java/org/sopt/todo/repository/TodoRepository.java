package org.sopt.todo.repository;

import org.sopt.todo.domain.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    int countByCategoryIdAndTargetDate(Long categoryId, LocalDate targetDate);

    // 카테고리별 + 날짜별 할 일 목록 (숨김/종료/삭제 제외)
    @Query("""
    SELECT t FROM TodoEntity t
    WHERE t.category.id = :categoryId
      AND t.targetDate = :date
      AND t.category.isStopped = false
""")
    List<TodoEntity> findByCategoryAndDate(Long categoryId, LocalDate date);

    @Query("""
        SELECT t FROM TodoEntity t
        WHERE t.category.id IN :categoryIds
          AND t.targetDate = :date
          AND t.category.isStopped = false
    """)
    List<TodoEntity> findByCategoryIdsAndDate(List<Long> categoryIds, LocalDate date);


}
