package org.sopt.todo.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.todo.domain.Todo;
import org.sopt.todo.domain.TodoEntity;
import org.sopt.todo.domain.dto.TodoDeleteResult;
import org.sopt.todo.exception.TodoCoreErrorCode;
import org.sopt.todo.exception.TodoNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.sopt.todo.exception.TodoCoreErrorCode.TODO_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class TodoFacade {

    private final TodoRetriever todoRetriever;
    private final TodoSaver todoSaver;
    private final TodoRemover todoRemover;
    private final TodoUpdater todoUpdater;

    public TodoEntity saveTodo(
            Long categoryId,
            String content,
            LocalDate targetDate,
            LocalTime startTime,
            boolean isCompleted,
            int order
    ) {
        return todoSaver.save(categoryId, content, targetDate, startTime, isCompleted, order);
    }

    public int getTodoCountByCategoryAndDate(Long categoryId, LocalDate targetDate) {
        return todoRetriever.countByCategoryIdAndTargetDate(categoryId, targetDate);
    }

    public List<Todo> getTodosByCategoryIdsAndDate(List<Long> categoryIds, LocalDate date) {
        return todoRetriever.findTodosByCategoryIdsAndDate(categoryIds, date);
    }

    public TodoEntity updateTodoContent(Long userId, Long todoId, String content) {
        return todoUpdater.updateContent(userId, todoId, content);
    }

    @Transactional
    public TodoDeleteResult deleteTodoAndGetCounts(Long userId, Long todoId) {
        //  삭제할 투두 조회 (삭제 전 날짜 파악)
        TodoEntity todo = todoRetriever.findByIdAndUserId(todoId, userId)
                .orElseThrow(() -> new TodoNotFoundException(TODO_NOT_FOUND));
        LocalDate targetDate = todo.getTargetDate();

        todoRemover.remove(userId, todoId);

        //  해당 날짜 기준으로 남은 개수 계산
        int completedCount = todoRetriever.countCompletedByUserIdAndTargetDate(userId, targetDate);
        int totalCount = todoRetriever.countByUserIdAndTargetDate(userId, targetDate);

        return new TodoDeleteResult(completedCount, totalCount);
    }
}
