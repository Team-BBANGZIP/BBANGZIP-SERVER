package org.sopt.todo.repository;

import org.sopt.todo.domain.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {

    // 카테고리 & 날짜 기준 투두 개수
    @Query("SELECT COUNT(t) FROM TodoEntity t WHERE t.category.id = :categoryId AND t.targetDate = :targetDate")
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
        ORDER BY t.category.id, t.order ASC
""")
    List<TodoEntity> findByCategoryIdsAndDate(List<Long> categoryIds, LocalDate date);

    // 특정 날짜의 전체 투두 개수
    @Query("SELECT COUNT(t) FROM TodoEntity t WHERE t.category.user.id = :userId AND t.targetDate = :targetDate")
    int countTotalByUserIdAndDate(@Param("userId") Long userId, @Param("targetDate") LocalDate targetDate);

    // 특정 날짜의 완료된 투두 개수
    @Query("SELECT COUNT(t) FROM TodoEntity t WHERE t.category.user.id = :userId AND t.targetDate = :targetDate AND t.isCompleted = true")
    int countCompletedByUserIdAndDate(@Param("userId") Long userId, @Param("targetDate") LocalDate targetDate);

    // 투두 삭제 (유저 검증 포함)
    @Modifying
    @Transactional
    @Query("DELETE FROM TodoEntity t WHERE t.id = :todoId AND t.category.user.id = :userId")
    int deleteByIdAndUserId(Long todoId, Long userId);

    @Query("SELECT t FROM TodoEntity t WHERE t.id = :todoId AND t.category.user.id = :userId")
    Optional<TodoEntity> findByIdAndUserId(@Param("todoId") Long todoId, @Param("userId") Long userId);

    // 투두 순서 업데이트
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("UPDATE TodoEntity t SET t.order = :order WHERE t.id = :todoId")
    void updateOrderByTodoId(@Param("todoId") Long todoId, @Param("order") int order);

    @Modifying
    @Query(value = """
    UPDATE todo t
    JOIN category c ON c.id = t.category_id
    SET t.order_index = t.order_index + 1
    WHERE c.user_id = :userId
      AND t.target_date = :targetDate
      AND t.order_index > :order
    """, nativeQuery = true)
    void incrementOrderAfter(Long userId, LocalDate targetDate, int order);
}