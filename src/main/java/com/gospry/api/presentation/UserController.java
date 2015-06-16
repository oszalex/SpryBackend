package com.gospry.api.presentation;

import com.gospry.api.domain.User;
import com.gospry.api.domain.UserLocation;
import com.gospry.api.domain.UserRegistration;
import com.gospry.api.exception.InvalidRequestException;
import com.gospry.api.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
public class UserController extends AbstractController {
    static Logger log = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/user/location", method = RequestMethod.POST)
    public String updateUserLocation(
            @RequestBody UserLocation location) throws InvalidRequestException {
        User user = getCurrentUser();
        user.updateLocation(location.getLongitude(), location.getLatitude());
        userRepository.save(user);
        //TODO: returnwert?
        return "";
    }

    public String updateUserInformation(
            @RequestBody UserRegistration data,
            @PathVariable(value = "phoneNumber") Long phoneNumber) throws InvalidRequestException {
        //TODO: Add/Change User Information
        return "";
    }
}
