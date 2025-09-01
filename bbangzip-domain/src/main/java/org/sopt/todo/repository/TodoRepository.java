package org.sopt.todo.repository;

import org.sopt.todo.domain.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    int countByCategoryIdAndTargetDate(Long categoryId, LocalDate targetDate);
}
