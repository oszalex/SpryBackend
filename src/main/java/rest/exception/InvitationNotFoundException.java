package rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Alex on 12.11.14.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvitationNotFoundException extends RuntimeException  {
    public InvitationNotFoundException(String matchId) {
        super("Unknown Invitation: " + matchId);
    }
}
