package org.sopt.user.dto.request;

import jakarta.validation.constraints.Size;

public record CommitmentMessageCreateRequest(

        @Size(max = 50, message = "다짐 메시지는 50자 이하여야 합니다.")
        String commitmentMessage
) {}
