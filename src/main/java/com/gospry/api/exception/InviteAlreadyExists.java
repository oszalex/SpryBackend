package com.gospry.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "invite already exists")
public class InviteAlreadyExists extends RuntimeException {
    public InviteAlreadyExists(String matchId) {
        super("Unknown match: " + matchId);
    }
}
