package org.sopt.todo.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.todo.exception.TodoNotFoundException;
import org.sopt.todo.repository.TodoRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static org.sopt.todo.exception.TodoCoreErrorCode.TODO_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class TodoRemover {
    private final TodoRepository todoRepository;

    @Transactional
    public void remove(Long userId, Long todoId) {
        int deletedCount = todoRepository.deleteByIdAndUserId(todoId, userId);
        if (deletedCount == 0) {
            throw new TodoNotFoundException(TODO_NOT_FOUND);
        }
    }
}