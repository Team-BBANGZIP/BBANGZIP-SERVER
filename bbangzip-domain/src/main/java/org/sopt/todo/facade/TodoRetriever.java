package org.sopt.todo.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.todo.domain.Todo;
import org.sopt.todo.domain.TodoEntity;
import org.sopt.todo.repository.TodoRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TodoRetriever {

    private final TodoRepository todoRepository;

    public int countByCategoryIdAndTargetDate(Long categoryId, LocalDate targetDate) {
        return todoRepository.countByCategoryIdAndTargetDate(categoryId, targetDate);
    }

    public List<Todo> findTodosByCategoryIdsAndDate(List<Long> categoryIds, LocalDate date) {
        List<TodoEntity> entities = todoRepository.findByCategoryIdsAndDate(categoryIds, date);
        return entities.stream()
                .map(TodoEntity::toDomain)
                .toList();
    }

    public int countTotalByUserIdAndDate(Long userId, LocalDate targetDate) {
        return todoRepository.countTotalByUserIdAndDate(userId, targetDate);
    }

    public int countCompletedByUserIdAndDate(Long userId, LocalDate targetDate) {
        return todoRepository.countCompletedByUserIdAndDate(userId, targetDate);
    }

    public Optional<TodoEntity> findByIdAndUserId(Long todoId, Long userId) {
        return todoRepository.findByIdAndUserId(todoId, userId);
    }
}
