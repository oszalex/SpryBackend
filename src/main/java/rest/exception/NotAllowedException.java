package rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Alex on 12.11.14.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotAllowedException extends RuntimeException  {
    public NotAllowedException(String matchId) {
        super("Unknown match: " + matchId);
    }
}
