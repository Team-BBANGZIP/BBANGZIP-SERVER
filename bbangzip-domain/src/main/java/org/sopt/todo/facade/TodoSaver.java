package org.sopt.todo.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.category.domain.CategoryEntity;
import org.sopt.category.facade.CategoryFacade;
import org.sopt.category.repository.CategoryRepository;
import org.sopt.todo.domain.Todo;
import org.sopt.todo.domain.TodoEntity;
import org.sopt.todo.repository.TodoRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class TodoSaver {

    private final TodoRepository todoRepository;
    private final CategoryRepository categoryRepository;

    public TodoEntity save(
            Long categoryId,
            String content,
            LocalDate targetDate,
            LocalTime startTime,
            boolean isCompleted,
            int order
    ) {
        // 프록시 - 실제로 DB SELECT 안 나감
        CategoryEntity categoryRef = categoryRepository.getReferenceById(categoryId);

        TodoEntity entity = TodoEntity.forCreate(
                content,
                categoryRef,
                targetDate,
                startTime,
                isCompleted,
                order
        );
        return todoRepository.save(entity);
    }

    // 복제된 투두 저장
    public TodoEntity saveCopiedTodo(
            Long categoryId,
            String content,
            LocalDate targetDate,
            int order
    ) {
        CategoryEntity categoryRef = categoryRepository.getReferenceById(categoryId);

        TodoEntity entity = TodoEntity.forCreate(
                content,
                categoryRef,
                targetDate,
                null,  // 복제 시 startTime은 null로 처리
                false, // isCompleted는 false로 설정
                order
        );

        return todoRepository.save(entity);
    }
}
