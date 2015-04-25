package com.gospry.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "invalid request dude")
public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String matchId) {
        super("Unknown match: " + matchId);
    }
}
