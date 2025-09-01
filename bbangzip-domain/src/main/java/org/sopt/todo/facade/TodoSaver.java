package org.sopt.todo.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.category.domain.CategoryEntity;
import org.sopt.category.facade.CategoryFacade;
import org.sopt.todo.domain.Todo;
import org.sopt.todo.domain.TodoEntity;
import org.sopt.todo.repository.TodoRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TodoSaver {

    private final TodoRepository todoRepository;
    private final CategoryFacade categoryFacade;

    public Todo save(final Todo todo) {

        Long userId = todo.getCategory().getUserId();
        Long categoryId = todo.getCategory().getId();

        CategoryEntity categoryEntity = categoryFacade.getEntityByIdAndUserId(categoryId, userId);
        TodoEntity entity = TodoEntity.forCreate(todo, categoryEntity);
        TodoEntity saved = todoRepository.save(entity);

        return saved.toDomain();

    }
}
