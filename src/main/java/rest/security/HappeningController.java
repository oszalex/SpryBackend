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
import rest.service.UserRepository;

/**
 * Created by Alex on 12.11.2014.
 */
@RestController
public class HappeningController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HappeningRepository happeningRepository;

    @RequestMapping("/happening/{happeningID}")
    public @ResponseBody Happening showHappening(@PathVariable(value="happeningID") Long happeningID) {

        if(!happeningRepository.exists(happeningID))
            throw new EventNotFoundException(happeningID.toString() + " Does not exist");

        //TODO: JSON nach validen parametern absuchen und dem Event hinzufügen
        // z.B: Preis, Dauer, Beschreibung,
        return happeningRepository.findOne(happeningID);
    }


    @RequestMapping(value="/happening",method = RequestMethod.POST)
    public @ResponseBody Happening createEvent(@RequestBody Happening newHappening) {
        //create Happening
        //newHappening.setCreator((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        happeningRepository.save(newHappening);
        return newHappening;
    }

    @RequestMapping(value="/happening/{happeningID}/{invitedUser}",method = RequestMethod.POST)
    public @ResponseBody Invitation invitePerson(@PathVariable(value="invitedUser") Long invitedUser,
                                                 @PathVariable(value="happeningID") Long happeningID,
                                                 @RequestBody Invitation newInvite) {
        //Wenn User nicht vorhanden dann erstellen
        if(!userRepository.exists(invitedUser)){
            User u = new User();
            u.setPhoneNumber(invitedUser);
            userRepository.save(u);
        }
        //TODO Test etc
        newInvite.setHappening(happeningRepository.findOne(happeningID));

        return newInvite;
    }
}
