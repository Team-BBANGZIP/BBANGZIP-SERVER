package org.sopt.todo.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.todo.domain.TodoEntity;
import org.sopt.todo.exception.InvalidTodoContentException;
import org.sopt.todo.exception.TodoNotFoundException;
import org.sopt.todo.repository.TodoRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.sopt.todo.exception.TodoCoreErrorCode.INVALID_TODO_CONTENT;
import static org.sopt.todo.exception.TodoCoreErrorCode.TODO_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class TodoUpdater {

    private final TodoRepository todoRepository;

    @Transactional
    public TodoEntity updateContent(Long userId, Long todoId, String content) {
        TodoEntity todo = getTodoOrThrow(userId, todoId);
        if (content.length() == 0) {
            throw new InvalidTodoContentException(INVALID_TODO_CONTENT);
        }
        todo.updateContent(content);
        return todo;
    }

    @Transactional
    public TodoEntity updateCompletion(Long userId, Long todoId, boolean isCompleted) {
        TodoEntity todo = getTodoOrThrow(userId, todoId);
        todo.updateCompletion(isCompleted);
        return todo;
    }

    @Transactional
    public TodoEntity updateStartTime(Long userId, Long todoId, LocalTime startTime) {
        TodoEntity todo = getTodoOrThrow(userId, todoId);
        todo.updateStartTime(startTime);
        return todo;
    }

    private TodoEntity getTodoOrThrow(Long userId, Long todoId) {
        return todoRepository.findByIdAndUserId(todoId, userId)
                .orElseThrow(() -> new TodoNotFoundException(TODO_NOT_FOUND));
    }

    public TodoEntity repeat(TodoEntity original, LocalDate targetDate, int newOrder) {
        TodoEntity newTodo = TodoEntity.forRepeat(original, targetDate, newOrder);
        return todoRepository.save(newTodo);
    }
}
