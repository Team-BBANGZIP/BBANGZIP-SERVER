package org.sopt.todo.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.category.domain.CategoryColor;
import org.sopt.category.domain.CategoryEntity;
import org.sopt.category.exception.CategoryCoreErrorCode;
import org.sopt.category.exception.CategoryNotFoundException;
import org.sopt.category.facade.CategoryRetriever;
import org.sopt.todo.domain.Todo;
import org.sopt.todo.domain.TodoEntity;
import org.sopt.todo.domain.command.TodoOrderUpdateCommand;
import org.sopt.todo.domain.dto.TodoDeleteResult;
import org.sopt.todo.exception.TodoCategoryColorMismatchException;
import org.sopt.todo.exception.TodoCategoryMismatchException;
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

    private final CategoryRetriever categoryRetriever;
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

    public int countTotalVisibleByUserIdAndDate(Long userId, LocalDate date, List<Long> visibleCategoryIds) {
        return todoRetriever.countTotalByUserIdAndDateAndCategoryIds(userId, date, visibleCategoryIds);
    }

    public int countCompletedVisibleByUserIdAndDate(Long userId, LocalDate date, List<Long> visibleCategoryIds) {
        return todoRetriever.countCompletedByUserIdAndDateAndCategoryIds(userId, date, visibleCategoryIds);
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

        List<Long> visibleCategoryIds = categoryRetriever.findVisibleCategoryIdsForTodoDate(userId, targetDate);
        int completedCount = todoRetriever.countCompletedByUserIdAndDateAndCategoryIds(userId, targetDate, visibleCategoryIds);
        int totalCount = todoRetriever.countTotalByUserIdAndDateAndCategoryIds(userId, targetDate, visibleCategoryIds);

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

        int originalOrder = originalTodo.getOrder();

        // 원본 뒤 투두들의 순서를 +1씩 증가
        todoUpdater.incrementOrderAfter(userId, originalTodo.getTargetDate(), originalOrder);

        // 복제된 투두를 원본 바로 뒤에 저장
        TodoEntity copiedTodo = todoSaver.saveCopiedTodo(
                originalTodo.getCategory().getId(),
                originalTodo.getContent(),
                originalTodo.getTargetDate(),
                originalOrder + 1
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

    /**
     * 여러 개 투두 순서 변경 및 카테고리 이동 처리
     */
    @Transactional
    public void updateTodoOrder(Long userId, TodoOrderUpdateCommand todoOrderUpdateCommand) {
        TodoEntity todo = todoRetriever.findByIdAndUserId(todoOrderUpdateCommand.todoId(), userId)
                .orElseThrow(() -> new TodoNotFoundException(TODO_NOT_FOUND));

        if (!todo.getCategory().getId().equals(todoOrderUpdateCommand.originCategoryId())) {
            throw new TodoCategoryMismatchException(TodoCoreErrorCode.TODO_CATEGORY_MISMATCH);
        }

        CategoryEntity targetCategory = categoryRetriever.findById(todoOrderUpdateCommand.targetCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(CategoryCoreErrorCode.CATEGORY_NOT_FOUND));

        if (!targetCategory.getColor().equals(CategoryColor.from(todoOrderUpdateCommand.targetCategoryColor()))) {
            throw new TodoCategoryColorMismatchException(TodoCoreErrorCode.TODO_CATEGORY_COLOR_MISMATCH);
        }

        if (!todoOrderUpdateCommand.originCategoryId().equals(todoOrderUpdateCommand.targetCategoryId())) {
            todoUpdater.moveCategory(todo, targetCategory);
        }

        todoUpdater.updateOrder(todoOrderUpdateCommand.todoList());
    }

    public void deleteAllByUserId(final Long userId) {
        todoRemover.deleteAllByUserId(userId);
    }
}