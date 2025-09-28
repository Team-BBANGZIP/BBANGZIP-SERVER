package org.sopt.todo.service;

import lombok.RequiredArgsConstructor;
import org.sopt.category.domain.Category;
import org.sopt.category.facade.CategoryFacade;
import org.sopt.todo.domain.Todo;
import org.sopt.todo.domain.TodoEntity;
import org.sopt.todo.domain.dto.TodoDeleteResult;
import org.sopt.todo.dto.req.TodoCreateReq;
import org.sopt.todo.dto.req.TodoUpdateContentReq;
import org.sopt.todo.dto.req.TodoUpdateStartTimeReq;
import org.sopt.todo.dto.res.*;
import org.sopt.todo.facade.TodoFacade;
import org.sopt.user.facade.UserFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoFacade todoFacade;
    private final CategoryFacade categoryFacade;
    private final UserFacade userFacade;

    @Transactional
    public TodoCreateRes createTodo(final long userId, final TodoCreateReq todoCreateReq) {
        userFacade.getUserById(userId);

        categoryFacade.getCategoryByIdAndUserId(todoCreateReq.categoryId(), userId);

        int order = todoFacade.getTodoCountByCategoryAndDate(todoCreateReq.categoryId(), todoCreateReq.targetDate());

        TodoEntity saved = todoFacade.saveTodo(
                todoCreateReq.categoryId(),
                todoCreateReq.content(),
                todoCreateReq.targetDate(),
                todoCreateReq.startTime(),
                false,
                order
        );

        return TodoCreateRes.from(saved.toDomain());
    }

    @Transactional(readOnly = true)
    public TodoListRes getTodosByDate(Long userId, LocalDate date) {
        String commitmentMessage = userFacade.getUserById(userId).getCommitmentMessage();
        List<Category> activeCategories = categoryFacade.getActiveCategoriesByUserId(userId);
        List<Long> activeCategoryIds = activeCategories.stream()
                .map(Category::getId)
                .toList();

        List<Todo> todos = todoFacade.getTodosByCategoryIdsAndDate(activeCategoryIds, date);

        // 카테고리별로 그룹화 & 투두 통계 집계
        List<TodoListRes.Category> categoryDtos = activeCategories.stream()
                .map(category -> {
                    List<TodoListRes.Category.Todo> todoDtos = todos.stream()
                            .filter(todo -> todo.getCategory().getId().equals(category.getId()))
                            .map(todo -> new TodoListRes.Category.Todo(
                                    todo.getId(),
                                    todo.getContent(),
                                    todo.isCompleted(),
                                    todo.getStartTime() != null ? todo.getStartTime().toString() : null
                            ))
                            .toList();
                    return new TodoListRes.Category(
                            category.getId(),
                            category.getName(),
                            category.getColor().name(),
                            todoDtos
                    );
                })
                .filter(categoryDto -> !categoryDto.todos().isEmpty())
                .toList();

        int totalCount = todos.size();
        int completedCount = (int) todos.stream().filter(Todo::isCompleted).count();

        return new TodoListRes(
                commitmentMessage,
                new TodoListRes.TodoSummary(
                        date.toString(),
                        totalCount,
                        completedCount
                ),
                categoryDtos
        );
    }

    public TodoUpdateContentRes updateTodoContent(Long userId, Long todoId, TodoUpdateContentReq todoUpdateContentReq) {
        TodoEntity updated = todoFacade.updateTodoContent(userId, todoId, todoUpdateContentReq.content());
        return new TodoUpdateContentRes(updated.getId(), updated.getContent());
    }

    @Transactional
    public TodoCompletionRes updateTodoCompletion(Long userId, Long todoId, boolean isCompleted) {

        todoFacade.updateTodoCompletion(userId, todoId, isCompleted);

        LocalDate targetDate = todoFacade.targetDateOf(todoId, userId);
        int completedCount = todoFacade.countCompletedByUserIdAndDate(userId, targetDate);
        int totalCount = todoFacade.countTotalByUserIdAndDate(userId, targetDate);

        return new TodoCompletionRes(todoId, isCompleted, completedCount, totalCount);
    }

    @Transactional
    public TodoDeleteRes deleteTodo(final long  userId, final long todoId) {
        userFacade.getUserById(userId);
        // Facade 호출 → Domain DTO 반환
        TodoDeleteResult todoDeleteResult = todoFacade.deleteTodoAndGetCounts(userId, todoId);
        return new TodoDeleteRes(todoDeleteResult.completedCount(), todoDeleteResult.totalCount());
    }

    @Transactional
    public TodoUpdateStartTimeRes updateTodoStartTime(Long userId, Long todoId, TodoUpdateStartTimeReq todoUpdateStartTimeReq) {
        TodoEntity updated = todoFacade.updateStartTime(userId, todoId, todoUpdateStartTimeReq.startTime());
        return new TodoUpdateStartTimeRes(updated.getId(), updated.getStartTime());
    }

    @Transactional
    public TodoCreateRes rescheduleTodo(Long userId, Long todoId, LocalDate targetDate) {
        TodoEntity newEntity = todoFacade.reschedule(userId, todoId, targetDate);
        return TodoCreateRes.from(newEntity.toDomain());
    }

}
