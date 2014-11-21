package rest.presentation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

    @RequestMapping(value="/status", method = RequestMethod.GET)
    public String statusInformation() {
        return "Spry is ready to rock!";
    }
}
