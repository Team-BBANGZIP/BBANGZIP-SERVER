package org.sopt.timer.dto.res;

public record TimerDoneRes(
        int count
) {
    public static TimerDoneRes of(int count) {
        return new TimerDoneRes(count);
    }
}