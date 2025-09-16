package org.sopt.todo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.code.SuccessCode;
import org.sopt.jwt.annotation.UserId;
import org.sopt.response.BaseResponse;
import org.sopt.todo.dto.req.TodoCreateReq;
import org.sopt.todo.dto.req.TodoUpdateContentReq;
import org.sopt.todo.dto.res.TodoCreateRes;
import org.sopt.todo.dto.res.TodoDeleteRes;
import org.sopt.todo.dto.res.TodoListRes;
import org.sopt.todo.service.TodoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/todos")
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<BaseResponse<TodoCreateRes>> createTodo(
            @UserId Long userId,
            @Valid @RequestBody final TodoCreateReq todoCreateReq
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success(SuccessCode.CREATED, todoService.createTodo(userId, todoCreateReq)));
    }

    @GetMapping
    public ResponseEntity<TodoListRes> getTodosByDate(
            @UserId Long userId,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) {
        return ResponseEntity.ok(todoService.getTodosByDate(userId, date));
    }

    @PatchMapping("/{todoId}")
    public ResponseEntity<Void> updateTodoContent(
            @UserId Long userId,
            @PathVariable final long todoId,
            @Valid @RequestBody final TodoUpdateContentReq todoUpdateContentReq
    ) {
        todoService.updateTodoContent(userId, todoId, todoUpdateContentReq);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<TodoDeleteRes>  deleteTodo(
            @UserId Long userId,
            @PathVariable("todoId") final Long todoId
    ) {
        TodoDeleteRes deleteRes = todoService.deleteTodo(userId, todoId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(deleteRes);
    }

}
