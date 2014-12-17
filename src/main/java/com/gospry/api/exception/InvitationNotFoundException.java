package com.gospry.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "No such Invitation")
public class InvitationNotFoundException extends RuntimeException {
    public InvitationNotFoundException(String matchId) {
        super("Unknown Invitation: " + matchId);
    }
}
