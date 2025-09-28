package org.sopt.timer.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.jwt.annotation.UserId;
import org.sopt.timer.dto.req.TimerDoneReq;
import org.sopt.timer.dto.res.TimerDoneRes;
import org.sopt.timer.service.TimerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/timers")
public class TimerController {

    private final TimerService timerService;

    @PostMapping
    public ResponseEntity<TimerDoneRes> timerDone(
            @UserId Long userId,
            @Valid @RequestBody TimerDoneReq req
    ){
        return ResponseEntity.ok(timerService.done(userId, req));
    }
}