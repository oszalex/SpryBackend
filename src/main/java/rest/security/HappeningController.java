package rest.security;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import rest.domain.Happening;
import rest.domain.Invitation;
import rest.domain.PasswordObject;
import rest.domain.User;
import rest.exception.EventNotFoundException;
import rest.exception.UsernameAlreadyInUseException;
import rest.exception.WrongTokenException;
import rest.service.HappeningRepository;
import rest.service.InvitationRepository;
import rest.service.UserRepository;

import java.util.List;

/**
 * Created by Alex on 12.11.2014.
 */
@RestController
public class HappeningController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HappeningRepository happeningRepository;
    @Autowired
    private InvitationRepository invitationRepository;

    /**
     * Returns the Event with the desired ID(if the Event is public, the User created the event or he is invited)
     * @param happeningID   Event the User wants to see
     * @return  The desired Happening
     */
    @RequestMapping(value="/happening/{happeningID}",method = RequestMethod.GET)
    public @ResponseBody Happening showHappening(@PathVariable(value="happeningID") Long happeningID) {

        if(!happeningRepository.exists(happeningID))
            throw new EventNotFoundException(happeningID.toString() + " Event does not exist");
        //TODO: Überprüfung ob User Event sehen darf
        return happeningRepository.findOne(happeningID);
    }

    /**
     * Updates the desired Event if the User is Creator or an Admin
     * @param happeningID
     * @return  the altered Happening
     */
    @RequestMapping(value="/happening/{happeningID}",method = RequestMethod.PUT)
    public @ResponseBody Happening updateHappening(@PathVariable(value="happeningID") Long happeningID, @RequestBody JSONPObject jason) {

        if(!happeningRepository.exists(happeningID))
            throw new EventNotFoundException(happeningID.toString() + " Event does not exist");
        Happening happy = happeningRepository.findOne(happeningID);
        UserDetailsAdapter x= (UserDetailsAdapter)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!happy.getCreator().equals(x.getUser()))
        {
            //TODO: Throw Not Allowed Exception
        }
        //TODO: JASON nach validen parametern absuchen und dem Event hinzufügen
        return happeningRepository.findOne(happeningID);
    }

    /**
     * Shows all the Happenings the User is allowed to see(Public, created, invited)
     * @return JSONobject containing all visible Happenings
     */
     @RequestMapping(value="/happening",method = RequestMethod.GET)
    public @ResponseBody List<Happening> showHappenings() {
         //TODO: only visible Events
        return (List)happeningRepository.findAll();
    }

    /**
     * Creates a new Happening
     * @param newHappening JSONObject that will be autoamtically parsed to a Happening
     * @return  The created happening
     */
    @RequestMapping(value="/happening",method = RequestMethod.POST)
    public @ResponseBody Happening createEvent(@RequestBody Happening newHappening) {
        UserDetailsAdapter x= (UserDetailsAdapter)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newHappening.setCreator(x.getUser());
        newHappening = happeningRepository.save(newHappening);
        return newHappening;
    }

    /**
     * Shows all invitations for a specific happening
     * @param happeningID
     * @return
     */
    @RequestMapping(value="/happening/{happeningID}/invited",method = RequestMethod.GET)
    public @ResponseBody List<Invitation> showInvites(@PathVariable(value="happeningID") Long happeningID) {

        if(!happeningRepository.exists(happeningID))
            throw new EventNotFoundException(happeningID.toString() + " Does not exist");

        return happeningRepository.findOne(happeningID).getInvitations();
    }
}
