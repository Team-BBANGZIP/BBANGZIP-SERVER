package org.sopt.auth.event;

public record SignUpCompletedEvent(long userId, String nickname) {}
