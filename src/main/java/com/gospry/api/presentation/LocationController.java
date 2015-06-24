package com.gospry.api.presentation;

import com.gospry.api.domain.Location;
import com.gospry.api.exception.InvalidRequestException;
import com.gospry.api.service.LocationRepository;
import com.gospry.api.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
public class LocationController extends AbstractController {
    static Logger log = Logger.getLogger(LocationController.class.getName());

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LocationRepository locationRepository;

    //TODO: DTO? + Checks
    @RequestMapping(value = "/location/get", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public
    @ResponseBody
    Location createLocation(@RequestBody Location newlocation) throws InvalidRequestException {
        Location temp = null;
        if (newlocation.getPubliclocation() == true) {
            temp = locationRepository.findByName(newlocation.getName());
        }

        if (temp != null) {
            return temp;
        } else newlocation = locationRepository.save(newlocation);
        return newlocation;
    }

    /*//TODO: Liefert Vorschläge zurück
    @RequestMapping(value = "/location", method = RequestMethod.GET)
    public String getLocation(
            @RequestBody UserRegistration data,
            @PathVariable(value = "phoneNumber") Long phoneNumber) throws InvalidRequestException {
        //TODO: Add/Change User Information
        return "";
    }*/
}
