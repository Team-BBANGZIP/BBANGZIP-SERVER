package org.sopt.jwt.core;

public record TokenId(long userId, String sessionId) {

    private static final String DELIMITER = ":";

    @Override
    public String toString() {
        return userId + DELIMITER + sessionId;
    }

}