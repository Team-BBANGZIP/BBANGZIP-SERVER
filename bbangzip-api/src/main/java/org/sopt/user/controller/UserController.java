package org.sopt.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.user.dto.req.CommitmentMessageCreateReq;
import org.sopt.user.dto.res.CommitmentMessageRes;
import org.sopt.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/commitments")
    public ResponseEntity<CommitmentMessageRes> createCommitmentMessage(
            // TODO: 커스텀 어노테이션  final Long userId,
            @Valid @RequestBody final CommitmentMessageCreateReq request
    ) {
        Long dummyUserId = 1L;
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createCommitmentMessage(dummyUserId, request));
    }
}
