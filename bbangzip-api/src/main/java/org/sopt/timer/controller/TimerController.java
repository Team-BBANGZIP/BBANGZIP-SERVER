package org.sopt.timer.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.jwt.annotation.UserId;
import org.sopt.timer.dto.req.TimerDoneReq;
import org.sopt.timer.dto.res.BreadListRes;
import org.sopt.timer.dto.res.TimerDoneRes;
import org.sopt.timer.dto.res.TodayBakedCountRes;
import org.sopt.timer.service.TimerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/today-count")
    public ResponseEntity<TodayBakedCountRes> getTodayCount(
            @UserId Long userId
    ) {
        return ResponseEntity.ok(timerService.getTodayBakedCount(userId));
    }

    @GetMapping("/breads")
    public ResponseEntity<BreadListRes> getBreads(
            @UserId Long userId
    ) {
        return ResponseEntity.ok(timerService.getBreadList(userId));
    }

}