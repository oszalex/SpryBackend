package com.gospry.api.presentation;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.gospry.api.domain.Happening;
import com.gospry.api.domain.Invitation;
import com.gospry.api.domain.User;
import com.gospry.api.exception.EventNotFoundException;
import com.gospry.api.security.UserDetailsAdapter;
import com.gospry.api.service.HappeningRepository;
import com.gospry.api.service.InvitationRepository;
import com.gospry.api.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
public class HappeningController {
    static Logger log = Logger.getLogger(HappeningController.class.getName());

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HappeningRepository happeningRepository;
    @Autowired
    private InvitationRepository invitationRepository;

    /**
     * Returns the Event with the desired ID(if the Event is public, the User created the event or he is invited)
     *
     * @param happeningID Event the User wants to see
     * @return The desired Happening
     */
    @RequestMapping(value = "/happening/{happeningID}", method = RequestMethod.GET)
    public
    @ResponseBody
    Happening showHappening(@PathVariable(value = "happeningID") Long happeningID) {

        if (!happeningRepository.exists(happeningID)) {
            log.warning("Event does not exist");
            throw new EventNotFoundException(happeningID.toString() + " Event does not exist");
        }

        //TODO: Überprüfung ob User Event sehen darf
        return happeningRepository.findOne(happeningID);
    }

    /**
     * Updates the desired Event if the User is Creator or an Admin
     *
     * @param happeningID
     * @return the altered Happening
     */
    @RequestMapping(value = "/happening/{happeningID}", method = RequestMethod.PUT)
    public
    @ResponseBody
    Happening updateHappening(@PathVariable(value = "happeningID") Long happeningID, @RequestBody JSONPObject jason) {

        if (!happeningRepository.exists(happeningID)) {
            log.warning("event does not exists dude!");
            throw new EventNotFoundException(happeningID.toString() + " Event does not exist");
        }

        Happening happy = happeningRepository.findOne(happeningID);
        UserDetailsAdapter x = (UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!happy.getCreator().equals(x.getUser())) {
            //TODO: Throw Not Allowed Exception
        }
        //TODO: JASON nach validen parametern absuchen und dem Event hinzufügen
        return happeningRepository.findOne(happeningID);
    }

    /**
     * Shows all Happenings on Server not for live
     * TODO: delete sooner or later
     *
     * @return JSONobject containing all visible Happenings
     */
    @RequestMapping(value = "/happening", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Happening> showHappenings() {
        //TODO: only visible Events
        return (List) happeningRepository.findAll();
    }

    /**
     * Shows all the Happenings the User is allowed to see(Public, created, invited)
     *
     * @return JSONobject containing all visible Happenings
     */
    @RequestMapping(value = "/myhappenings", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Happening> myHappenings() {
        //TODO: only visible Events
        List<Happening> myhappenings;
        UserDetailsAdapter x = (UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User loggedin = x.getUser();
        myhappenings = loggedin.gethappenings();

        return (List) happeningRepository.findAll();
    }

    /**
     * Creates a new Happening
     *
     * @param newHappening JSONObject that will be automatically parsed to a Happening
     * @return The created happening
     */
    @RequestMapping(value = "/happening", method = RequestMethod.POST)
    public
    @ResponseBody
    Happening createEvent(@RequestBody Happening newHappening) {
        UserDetailsAdapter x = (UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newHappening.setCreator(x.getUser());
        newHappening = happeningRepository.save(newHappening);
        return newHappening;
    }

    /**
     * Shows all invitations for a specific happening
     *
     * @param happeningID
     * @return
     */
    @RequestMapping(value = "/happening/{happeningID}/invited", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Invitation> showInvites(@PathVariable(value = "happeningID") Long happeningID) {

        if (!happeningRepository.exists(happeningID)) {
            log.warning("event does not exist");
            throw new EventNotFoundException(happeningID.toString() + " Does not exist");
        }


        return happeningRepository.findOne(happeningID).getInvitations();
    }
}
