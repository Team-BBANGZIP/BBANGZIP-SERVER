package org.sopt.todo.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.todo.domain.Todo;
import org.sopt.todo.domain.TodoEntity;
import org.sopt.todo.domain.dto.TodoDeleteResult;
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
    public void updateTodoCompletion(Long userId, Long todoId, boolean isCompleted) {
        todoUpdater.updateCompletion(userId, todoId, isCompleted);
    }

    public int countTotalByUserIdAndDate(Long userId, LocalDate date) {
        return todoRetriever.countTotalByUserIdAndDate(userId, date);
    }

    public int countCompletedByUserIdAndDate(Long userId, LocalDate date) {
        return todoRetriever.countCompletedByUserIdAndDate(userId, date);
    }

    public LocalDate targetDateOf(Long todoId, Long userId) {
        return todoRetriever.findByIdAndUserId(todoId, userId)
                .map(TodoEntity::getTargetDate)
                .orElseThrow(() -> new TodoNotFoundException(TODO_NOT_FOUND));
    }

    @Transactional
    public TodoDeleteResult deleteTodoAndGetCounts(Long userId, Long todoId) {
        //  삭제할 투두 조회 (삭제 전 날짜 파악)
        TodoEntity todo = todoRetriever.findByIdAndUserId(todoId, userId)
                .orElseThrow(() -> new TodoNotFoundException(TODO_NOT_FOUND));
        LocalDate targetDate = todo.getTargetDate();

        todoRemover.remove(userId, todoId);

        //  해당 날짜 기준으로 남은 개수 계산
        int completedCount = todoRetriever.countCompletedByUserIdAndDate(userId, targetDate);
        int totalCount = todoRetriever.countTotalByUserIdAndDate(userId, targetDate);

        return new TodoDeleteResult(completedCount, totalCount);
    }

    public TodoEntity updateStartTime(Long userId, Long todoId, LocalTime startTime) {
        return todoUpdater.updateStartTime(userId, todoId, startTime);
    }

    @Transactional
    public TodoEntity repeatTodo(Long userId, Long todoId, LocalDate targetDate) {
        TodoEntity origin = todoRetriever.findByIdAndUserId(todoId, userId)
                .orElseThrow(() -> new TodoNotFoundException(TODO_NOT_FOUND));

        // 새 날짜의 order 계산
        int newOrder = todoRetriever.countTotalByUserIdAndDate(userId, targetDate);

        return todoUpdater.repeat(origin, targetDate, newOrder);
    }

    @Transactional
    public TodoEntity copyTodo(Long userId, Long todoId) {

        TodoEntity originalTodo = todoRetriever.findByIdAndUserId(todoId, userId)
                .orElseThrow(() -> new TodoNotFoundException(TODO_NOT_FOUND));

        int order = todoRetriever.countTotalByUserIdAndDate(userId, originalTodo.getTargetDate());

        TodoEntity copiedTodo = todoSaver.saveCopiedTodo(
                originalTodo.getCategory().getId(),
                originalTodo.getContent(),
                originalTodo.getTargetDate(),
                order
        );

        return copiedTodo;
    }

    @Transactional
    public TodoEntity rescheduleTodo(Long userId, Long todoId, LocalDate requestDate) {
        TodoEntity origin = todoRetriever.findByIdAndUserId(todoId, userId)
                .orElseThrow(() -> new TodoNotFoundException(TODO_NOT_FOUND));

        // 요청이 null이면 기존 날짜 +1일
        LocalDate newDate = (requestDate == null)
                ? origin.getTargetDate().plusDays(1)
                : requestDate;

        // 새 날짜의 정렬 순서 계산
        int newOrder = todoRetriever.countTotalByUserIdAndDate(userId, newDate);

        // 기존 투두 삭제 후 동일 내용으로 새로 저장
        todoRemover.remove(userId, todoId);

        return todoSaver.save(
                origin.getCategory().getId(),
                origin.getContent(),
                newDate,
                origin.getStartTime(),
                false,
                newOrder
        );
    }
}
