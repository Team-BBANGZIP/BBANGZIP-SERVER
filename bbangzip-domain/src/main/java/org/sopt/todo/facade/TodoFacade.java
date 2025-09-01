package org.sopt.todo.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.todo.domain.Todo;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class TodoFacade {

    private final TodoRetriever todoRetriever;
    private final TodoSaver todoSaver;

    public Todo saveTodo(final Todo todo) {
        return todoSaver.save(todo);
    }

    public int getTodoCountByCategoryAndDate(Long categoryId, LocalDate targetDate) {
        return todoRetriever.countByCategoryIdAndTargetDate(categoryId, targetDate);
    }
}
