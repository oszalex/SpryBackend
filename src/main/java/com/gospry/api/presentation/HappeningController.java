package com.gospry.api.presentation;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.gospry.api.domain.*;
import com.gospry.api.exception.EventNotFoundException;
import com.gospry.api.exception.NotAllowedException;
import com.gospry.api.service.HappeningRepository;
import com.gospry.api.service.InvitationRepository;
import com.gospry.api.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@RestController
public class HappeningController extends AbstractController {
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
    HappeningDTO showHappening(@PathVariable(value = "happeningID") Long happeningID) {
        InvitationStatus status;
        Happening happening = happeningRepository.findOne(happeningID);
        if (happening == null) {
            log.warning("Event does not exist" + happeningID);
            throw new EventNotFoundException(happeningID.toString() + " Event does not exist");
        }
        if (!happening.getIsPublic()) {
            Invitation invitation = invitationRepository.findByInvitedUserAndHappening(getCurrentUser(), happeningRepository.findOne(happeningID));
            if (invitation == null) {
                log.warning("User " + getCurrentUser().getUserID() + "not allowed to see Happening" + happeningID);
                throw new NotAllowedException("User " + getCurrentUser().getUserID() + "not allowed to see Happening" + happeningID);
            }
            status = invitation.getStatus();
        } else {
            status = InvitationStatus.INVITED;
        }
        return new HappeningDTO(status, happening);
        //   return happeningRepository.findOne(happeningID);
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

        if (!happy.getCreator().equals(getCurrentUser())) {
            //TODO: Throw Not Allowed Exception
        }
        //TODO: JASON nach validen parametern absuchen und dem Event hinzufügen
        return happeningRepository.findOne(happeningID);
    }

    /**
     * Shows all Happenings on Server not for live
     */
    @RequestMapping(value = "/happening", method = RequestMethod.GET)
    public
    @ResponseBody
    Set<HappeningDTO> list() {
        HashSet<Happening> happenings = new HashSet<>();
        //Warum Hashset?
        // get all created
        happenings.addAll(happeningRepository.findByCreator(getCurrentUser()));

        // get all invited
        for (Invitation i : invitationRepository.findByInvitedUser(getCurrentUser())) {
            happenings.add(i.getHappening());
        }

        // get all public
        //TODO: filter all public and only add 'well selected'
        happenings.addAll(happeningRepository.findByIsPublic(true));
        //TODO: schöner machen
        HashSet<HappeningDTO> happeningsDTO = new HashSet<>();
        for (Happening happy : happenings) {
            InvitationStatus status;
            Invitation i = invitationRepository.findByInvitedUserAndHappening(getCurrentUser(), happy);
            if (i == null) status = InvitationStatus.INVITED;
            else status = i.getStatus();
            happeningsDTO.add(new HappeningDTO(status, happy));
        }
        //TODO: sort? hier sortieren bringt nix
        return happeningsDTO;
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
        try {
            User currentUser = getCurrentUser();
            newHappening.setCreator(currentUser);
            newHappening = happeningRepository.save(newHappening);
            getCurrentUser().addcreatedHappening(newHappening);
            userRepository.save(currentUser);
        } catch (Exception e) {
            System.out.print(e.toString());
        }
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
