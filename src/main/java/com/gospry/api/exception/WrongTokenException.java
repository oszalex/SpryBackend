package com.gospry.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongTokenException extends RuntimeException  {
    public WrongTokenException(String matchId) {
        super("Unknown match: " + matchId);
    }
}
