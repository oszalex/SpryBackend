package com.gospry.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Not allowed")
public class NotAllowedException extends RuntimeException {
    public NotAllowedException(String matchId) {
        super("Unknown match: " + matchId);
    }
}
