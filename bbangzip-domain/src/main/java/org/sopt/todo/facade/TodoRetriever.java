package org.sopt.todo.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.category.domain.CategoryEntity;
import org.sopt.category.repository.CategoryRepository;
import org.sopt.todo.domain.Todo;
import org.sopt.todo.domain.TodoEntity;
import org.sopt.todo.domain.TodoEntity;
import org.sopt.todo.repository.TodoRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TodoRetriever {

    private final TodoRepository todoRepository;

    public int countByCategoryIdAndTargetDate(Long categoryId, LocalDate targetDate) {
        return todoRepository.countByCategoryIdAndTargetDate(categoryId, targetDate);
    }

    public int countByUserIdAndTargetDate(Long userId, LocalDate targetDate) {
        return todoRepository.countByUserIdAndTargetDate(userId, targetDate);
    }

    public int countCompletedByUserIdAndTargetDate(Long userId, LocalDate targetDate) {
        return todoRepository.countCompletedByUserIdAndTargetDate(userId, targetDate);
    }

    public Optional<TodoEntity> findByIdAndUserId(Long todoId, Long userId) {
        return todoRepository.findByIdAndUserId(todoId, userId);
    }
}
