package org.sopt.todo.service;

import lombok.RequiredArgsConstructor;
import org.sopt.category.domain.Category;
import org.sopt.category.facade.CategoryFacade;
import org.sopt.todo.domain.Todo;
import org.sopt.todo.domain.TodoEntity;
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
}
