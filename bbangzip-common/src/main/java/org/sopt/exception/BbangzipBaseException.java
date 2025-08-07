package org.sopt.exception;

public abstract class BbangzipBaseException extends RuntimeException {
    public BbangzipBaseException() {
        super();
    }

    public BbangzipBaseException(String message) {
        super(message);
    }

    public BbangzipBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BbangzipBaseException(Throwable cause) {
        super(cause);
    }
}
