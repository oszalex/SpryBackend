package com.gospry.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "username already exists")
public class UsernameAlreadyInUseException extends RuntimeException {
    public UsernameAlreadyInUseException(String matchId) {
        super("Unknown match: " + matchId);
    }
}
