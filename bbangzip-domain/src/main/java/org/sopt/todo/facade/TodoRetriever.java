package org.sopt.todo.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.category.domain.CategoryEntity;
import org.sopt.category.repository.CategoryRepository;
import org.sopt.todo.domain.Todo;
import org.sopt.todo.domain.TodoEntity;
import org.sopt.todo.repository.TodoRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TodoRetriever {

    private final TodoRepository todoRepository;
    private final CategoryRepository categoryRepository;

    public int countByCategoryIdAndTargetDate(Long categoryId, LocalDate targetDate) {
        return todoRepository.countByCategoryIdAndTargetDate(categoryId, targetDate);
    }

    public List<Todo> findTodosByCategoryIdsAndDate(List<Long> categoryIds, LocalDate date) {
        List<TodoEntity> entities = todoRepository.findByCategoryIdsAndDate(categoryIds, date);
        return entities.stream()
                .map(TodoEntity::toDomain)
                .toList();
    }
}
