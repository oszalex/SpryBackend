package rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by chris on 08.11.14.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class InviteAlreadyExists extends RuntimeException {
    public InviteAlreadyExists(String matchId) {
        super("Unknown match: " + matchId);
    }
}
