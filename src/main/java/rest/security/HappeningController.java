package rest.security;

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

    @RequestMapping("/happening/{happeningID}")
    public @ResponseBody Happening showHappening(@PathVariable(value="happeningID") Long happeningID) {

        if(!happeningRepository.exists(happeningID))
            throw new EventNotFoundException(happeningID.toString() + " Event does not exist");

        //TODO: JSON nach validen parametern absuchen und dem Event hinzufügen
        //TODO: Überprüfung ob User Event sehen darf
        // z.B: Preis, Dauer, Beschreibung,
        return happeningRepository.findOne(happeningID);
    }

     @RequestMapping(value="/happening",method = RequestMethod.GET)
    public @ResponseBody List<Happening> showHappenings() {
        return (List)happeningRepository.findAll();
    }


    @RequestMapping(value="/happening/{happeningID}/invited",method = RequestMethod.GET)
    public @ResponseBody List<Invitation> showInvites(@PathVariable(value="happeningID") Long happeningID) {

        if(!happeningRepository.exists(happeningID))
            throw new EventNotFoundException(happeningID.toString() + " Does not exist");

        return happeningRepository.findOne(happeningID).getInvitations();
    }
/*
    @RequestMapping(value="/happening",method = RequestMethod.GET)
    public @ResponseBody List<Invitation> showHappenings() {
        UserDetailsAdapter x= (UserDetailsAdapter)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (List)x.getUser().getinvited_happenings();
    }
*/
    @RequestMapping(value="/happening",method = RequestMethod.POST)
    public @ResponseBody Happening createEvent(@RequestBody Happening newHappening) {
        //create Happening
        UserDetailsAdapter x= (UserDetailsAdapter)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newHappening.setCreator(x.getUser());
        happeningRepository.save(newHappening);
        return newHappening;
    }

    @RequestMapping(value="/happening/{happeningID}/{invitedUser}",method = RequestMethod.PUT)
    public @ResponseBody Invitation updateInvitation(@PathVariable(value="invitedUser") Long invitedUser,
                                                 @PathVariable(value="happeningID") Long happeningID,
                                                 @RequestBody Invitation newInvite) {
        //Wenn Invitation vorhanden dann updaten wenn eingeloggter User gleich invitedUser ist
        // oder invitation entfernen wenn eingeloggter user creator is und invitationstatus gleich not invited ist
        //Wenn User nicht vorhanden dann erstellen
        User u;
        if (!userRepository.exists(invitedUser)) {
            u = new User();
            u.setPhoneNumber(invitedUser);
            userRepository.save(u);
        } else {
            u = userRepository.findByUserID(invitedUser);
        }
        newInvite.setinvited_User(u);
        newInvite.setHappening(happeningRepository.findOne(happeningID));
        //TODO: Check ob bereits eingeladen? happening existiert? eindeutig?
        UserDetailsAdapter x= (UserDetailsAdapter)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newInvite.setInviter(x.getUser());
        invitationRepository.save(newInvite);
        return newInvite;
    }

    /**
     *
     * @param invitedUser   The User who is invited to an event
     * @param happeningID   The Event the User is invited to
     * @param newInvite     The Invitationobject that is created
     *                      //TODO: Nur Status schicken? restliche Info is eh da und wird mit Settern gesetzt
     * @return
     */


    @RequestMapping(value="/happening/{happeningID}/{invitedUser}",method = RequestMethod.POST)
    public @ResponseBody Invitation invitePerson(@PathVariable(value="invitedUser") Long invitedUser,
                                                 @PathVariable(value="happeningID") Long happeningID,
                                                 @RequestBody Invitation newInvite) {
        if(!happeningRepository.exists((happeningID))){
            throw new EventNotFoundException(happeningID.toString() + " Does not exist");
        }
        UserDetailsAdapter x = (UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User inviter = x.getUser();
        User invited;
        if (!userRepository.exists(invitedUser)) {
            invited = new User();
            invited.setPhoneNumber(invitedUser);
            userRepository.save(invited);
        } else {
            invited = userRepository.findByUserID(invitedUser);
        }
            newInvite.setinvited_User(invited);
            newInvite.setHappening(happeningRepository.findOne(happeningID));
            newInvite.setInviter(inviter);
            invitationRepository.save(newInvite);
            return newInvite;

    }
}
