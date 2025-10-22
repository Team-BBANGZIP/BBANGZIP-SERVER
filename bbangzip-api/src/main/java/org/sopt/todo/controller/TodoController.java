package org.sopt.todo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.code.SuccessCode;
import org.sopt.jwt.annotation.UserId;
import org.sopt.response.BaseResponse;
import org.sopt.todo.dto.req.*;
import org.sopt.todo.dto.res.*;
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

    @PatchMapping("/{todoId}/completion")
    public ResponseEntity<TodoCompletionRes> updateTodoCompletion(
            @UserId Long userId,
            @PathVariable final Long todoId,
            @RequestBody final TodoCompletionReq todoCompletionReq
    ) {
        return ResponseEntity.ok(todoService.updateTodoCompletion(userId, todoId, todoCompletionReq.isCompleted()));
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<TodoDeleteRes>  deleteTodo(
            @UserId Long userId,
            @PathVariable("todoId") final Long todoId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(todoService.deleteTodo(userId, todoId));
    }

    @PatchMapping("/{todoId}/start-time")
    public ResponseEntity<TodoUpdateStartTimeRes> updateTodoStartTime(
            @UserId Long userId,
            @PathVariable Long todoId,
            @RequestBody @Valid TodoUpdateStartTimeReq todoUpdateStartTimeReq
    ) {
        return ResponseEntity.ok(todoService.updateTodoStartTime(userId, todoId, todoUpdateStartTimeReq));
    }

    @PostMapping("/{todoId}/repeat")
    public ResponseEntity<BaseResponse<TodoCreateRes>> repeatTodo(
            @UserId Long userId,
            @PathVariable Long todoId,
            @RequestBody @Valid TodoRepeatReq todoRepeatReq
    ) {
        TodoCreateRes newTodo = todoService.repeatTodo(userId, todoId, todoRepeatReq.getTargetDate());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success(SuccessCode.CREATED, newTodo));
    }

    @PostMapping("/{todoId}/copy")
    public ResponseEntity<BaseResponse<TodoCreateRes>> copyTodo(
            @UserId Long userId,
            @PathVariable Long todoId
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success(SuccessCode.CREATED, todoService.copyTodo(userId, todoId)));
    }

    @PostMapping("/{todoId}/reschedule")
    public ResponseEntity<BaseResponse<TodoCreateRes>> rescheduleTodo(
            @UserId Long userId,
            @PathVariable Long todoId,
            @Valid @RequestBody TodoRescheduleReq todoRescheduleReq
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success(SuccessCode.CREATED, todoService.rescheduleTodo(userId, todoId, todoRescheduleReq)));
    }

    @PatchMapping("/reorder")
    public ResponseEntity<Void> updateTodoOrder(
            @UserId Long userId,
            @Valid @RequestBody final TodoOrderUpdateReq todoOrderUpdateReq
    ) {
        todoService.updateTodoOrder(userId, todoOrderUpdateReq);
        return ResponseEntity.ok().build();
    }
}
