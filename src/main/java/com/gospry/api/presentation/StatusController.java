package com.gospry.api.presentation;

import com.gospry.api.service.HappeningRepository;
import com.gospry.api.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/status")
public class StatusController {
    @Autowired
    HappeningRepository happeningRepository;
    @Autowired
    UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView statusInformation() {
        ModelAndView model = new ModelAndView("status");

        model.addObject("happenings", happeningRepository.findAll());
        model.addObject("users", userRepository.findAll());

        return model;
    }
}
