package org.sopt.test;

import jakarta.validation.constraints.NotBlank;

public record TestSecurity(
        @NotBlank
        String name,
        String email
) {
}