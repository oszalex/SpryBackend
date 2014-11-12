package rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by chris on 12.11.14.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EventNotFoundException extends RuntimeException  {
    public EventNotFoundException(String matchId) {
        super("Unknown match: " + matchId);
    }
}
