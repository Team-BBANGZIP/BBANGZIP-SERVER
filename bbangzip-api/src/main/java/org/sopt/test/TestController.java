package org.sopt.test;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.sopt.auth.annotation.UserId;
import org.sopt.auth.jwt.JwtTokenProvider;
import org.sopt.auth.jwt.dto.JwtTokensDto;
import org.sopt.user.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/success")
    public ResponseEntity<TestDto> testSuccess(){
        return ResponseEntity.ok(TestDto.builder().content("얼른 자고싶어..").build());
    }

    @GetMapping("/token/{userId}")
    public ResponseEntity<JwtTokensDto> testToken(
            @PathVariable final Long userId
    ) {
        JwtTokensDto tokens = jwtTokenProvider.issueTokens(userId);
        return ResponseEntity.ok(tokens);
    }

    @GetMapping("/security")
    public ResponseEntity<TestDto> testSecurity(
            @UserId final Long userId,
            @Valid @RequestBody final TestSecurity testSecurity
    ) {
        return ResponseEntity.ok(TestDto.builder().content(testSecurity.name() + " " + userId).build());
    }


    @GetMapping("/db")
    public ResponseEntity<TestDto> testDbConnection() {
        try {
            Long count = userRepository.count();
            return ResponseEntity.ok(TestDto.builder()
                .content("✅ DB 연결 성공! 현재 사용자 수: " + count)
                .build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                TestDto.builder()
                    .content("❌ DB 연결 실패: " + e.getMessage())
                    .build()
            );
        }
    }


}