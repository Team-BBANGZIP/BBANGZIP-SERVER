package org.sopt.todo.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.todo.domain.TodoEntity;
import org.sopt.todo.exception.InvalidTodoContentException;
import org.sopt.todo.exception.TodoCoreErrorCode;
import org.sopt.todo.exception.TodoNotFoundException;
import org.sopt.todo.repository.TodoRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static org.sopt.todo.exception.TodoCoreErrorCode.INVALID_TODO_CONTENT;
import static org.sopt.todo.exception.TodoCoreErrorCode.TODO_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class TodoUpdater {

    private final TodoRepository todoRepository;

    @Transactional
    public TodoEntity updateContent(Long userId, Long todoId, String content) {
        TodoEntity todo = todoRepository.findByIdAndUserId(todoId, userId)
                .orElseThrow(() -> new TodoNotFoundException(TODO_NOT_FOUND));

        if (content.length() == 0) {
            throw new InvalidTodoContentException(INVALID_TODO_CONTENT);
        }

        todo.updateContent(content);
        return todo;
    }
}
