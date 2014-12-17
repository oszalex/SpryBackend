package com.gospry.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "wrong token dude")
public class WrongTokenException extends RuntimeException {
    public WrongTokenException(String matchId) {
        super("Unknown match: " + matchId);
    }
}
