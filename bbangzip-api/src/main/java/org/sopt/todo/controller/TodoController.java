package org.sopt.todo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.code.SuccessCode;
import org.sopt.response.BaseResponse;
import org.sopt.todo.dto.req.TodoCreateReq;
import org.sopt.todo.dto.res.TodoCreateRes;
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
            // TODO: 커스텀 어노테이션 final Long userId,
            @Valid @RequestBody final TodoCreateReq todoCreateReq
    ) {
        Long dummyUserId = 1L;

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success(SuccessCode.CREATED, todoService.createTodo(dummyUserId, todoCreateReq)));
    }

    @GetMapping
    public ResponseEntity<TodoListRes> getTodosByDate(
            // TODO: 커스텀 어노테이션 final Long userId,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) {
        Long dummyUserId = 1L;
        return ResponseEntity.ok(todoService.getTodosByDate(dummyUserId, date));
    }

}
