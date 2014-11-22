package com.gospry.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="No such Event")
public class EventNotFoundException extends RuntimeException  {
    public EventNotFoundException(String matchId) {
        super("Unknown match: " + matchId);
    }
}
