package rest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rest.items.Act;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ActController {
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/act/{id}")
    public Act acting(@PathVariable("id") Integer id) {
        return Act.demoAct();
    }


    @RequestMapping("/actings")
    public List<Act> actings() {
        List<Act> acts = new ArrayList<Act>();

        acts.add(Act.demoAct());

        return acts;
    }
}