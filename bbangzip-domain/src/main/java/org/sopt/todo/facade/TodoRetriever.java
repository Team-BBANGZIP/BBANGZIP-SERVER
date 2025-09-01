package org.sopt.todo.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.todo.repository.TodoRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class TodoRetriever {

    private final TodoRepository todoRepository;

    public int countByCategoryIdAndTargetDate(Long categoryId, LocalDate targetDate) {
        return todoRepository.countByCategoryIdAndTargetDate(categoryId, targetDate);
    }
}
