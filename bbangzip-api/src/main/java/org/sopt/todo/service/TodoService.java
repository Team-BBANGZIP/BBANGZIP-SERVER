package org.sopt.todo.service;

import lombok.RequiredArgsConstructor;
import org.sopt.category.domain.Category;
import org.sopt.category.facade.CategoryFacade;
import org.sopt.todo.domain.Todo;
import org.sopt.todo.dto.req.TodoCreateReq;
import org.sopt.todo.dto.res.TodoCreateRes;
import org.sopt.todo.facade.TodoFacade;
import org.sopt.user.facade.UserFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoFacade todoFacade;
    private final CategoryFacade categoryFacade;
    private final UserFacade userFacade;

    @Transactional
    public TodoCreateRes createTodo(final long userId, final TodoCreateReq todoCreateReq) {
        userFacade.getUserById(userId);

        Category category = categoryFacade.getCategoryByIdAndUserId(todoCreateReq.categoryId(), userId);

        int order = todoFacade.getTodoCountByCategoryAndDate(category.getId(), todoCreateReq.targetDate());

        Todo todo = Todo.builder()
                .category(category)
                .content(todoCreateReq.content())
                .startTime(todoCreateReq.startTime())
                .isCompleted(false)
                .targetDate(todoCreateReq.targetDate())
                .order(order)
                .build();

        Todo saved = todoFacade.saveTodo(todo);
        return TodoCreateRes.from(saved);
    }

}
