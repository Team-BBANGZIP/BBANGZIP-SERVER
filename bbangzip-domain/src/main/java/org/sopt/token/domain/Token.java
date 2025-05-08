package org.sopt.token.domain;

import lombok.Getter;

@Getter
public class Token {

    private final String refreshToken;

    public Token(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public static Token fromEntity(final TokenEntity tokenEntity) {
        return new Token(
                tokenEntity.getRefreshToken()
        );
    }
}