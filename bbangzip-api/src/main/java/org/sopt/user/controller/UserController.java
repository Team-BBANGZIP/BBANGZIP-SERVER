package org.sopt.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.jwt.annotation.UserId;
import org.sopt.user.domain.User;
import org.sopt.user.dto.req.CommitmentMessageCreateReq;
import org.sopt.user.dto.req.UserProfileUpdateReq;
import org.sopt.user.dto.res.CommitmentMessageRes;
import org.sopt.user.dto.res.UserProfileRes;
import org.sopt.user.dto.res.UserProfileUpdateRes;
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
            @UserId Long userId,
            @Valid @RequestBody final CommitmentMessageCreateReq commitmentMessageCreateReq
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createCommitmentMessage(userId, commitmentMessageCreateReq));
    }

    @PatchMapping("/profile")
    public ResponseEntity<UserProfileUpdateRes> updateProfile(
            @UserId Long userId,
            @Valid @RequestBody UserProfileUpdateReq userProfileUpdateReq
    ) {
        User updatedProfile = userService.updateProfile(userId, userProfileUpdateReq);
        return ResponseEntity
                .ok(UserProfileUpdateRes.from(updatedProfile));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileRes> getProfile(
            @UserId Long userId
    ) {
        User userProfile = userService.getProfile(userId);
        return ResponseEntity
                .ok(UserProfileRes.from(userProfile));
    }

}
