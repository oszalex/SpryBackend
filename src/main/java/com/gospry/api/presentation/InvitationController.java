package com.gospry.api.presentation;

import com.gospry.api.domain.Happening;
import com.gospry.api.domain.Invitation;
import com.gospry.api.domain.InvitationStatus;
import com.gospry.api.domain.User;
import com.gospry.api.exception.EventNotFoundException;
import com.gospry.api.exception.InvitationNotFoundException;
import com.gospry.api.exception.NotAllowedException;
import com.gospry.api.security.UserDetailsAdapter;
import com.gospry.api.service.HappeningRepository;
import com.gospry.api.service.InvitationRepository;
import com.gospry.api.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Alex on 16.11.2014.
 */
@RestController
public class InvitationController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HappeningRepository happeningRepository;
    @Autowired
    private InvitationRepository invitationRepository;

    /**
     * Eine Person zu einem Event einladen
     * @param invitedUser   User der eingeladen wird
     * @param happeningID   Event zu dem eingeladen wird
     * @param invitestatus     Ein Invitationobjekt mit Invitationsstatus
     * @return
     */
    @RequestMapping(value="/invitation/{happeningID}/{invitedUser}",method = RequestMethod.POST)
    public @ResponseBody
    Invitation invite(@PathVariable(value="invitedUser") Long invitedUser,
                                                 @PathVariable(value="happeningID") Long happeningID
                                                 ,@RequestBody Invitation invitestatus) {
        Invitation newInvite = new Invitation();
        newInvite.setStatus(InvitationStatus.INVITED);
        UserDetailsAdapter x = (UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newInvite.setInviter(x.getUser());
        User invited;
        long a = invitationRepository.count();
        if (!userRepository.exists(invitedUser)) {
            invited = new User();
            invited.setPhoneNumber(invitedUser);
            invited = userRepository.save(invited);
        }
        else {
            invited = userRepository.findByUserID(invitedUser);
        }
        if(!happeningRepository.exists(happeningID)){

            throw new EventNotFoundException("Happening does not exist");
        }
        Happening happy = happeningRepository.findOne(happeningID);
        if (happy.getCreator().getUserID() != x.getUser().getUserID())
        {
            throw new NotAllowedException("Wrong User?");
            //TODO:Check ob User ADMIN ist, dann wärs OK
        }
        newInvite.setHappening(happy);
        for(Invitation temp: invited.getinvited_happenings())
        {
            if(temp.getHappening().equals(newInvite.getHappening()))
            {
               return temp;
            }
        }
        newInvite.setinvited_User(invited);
        invitationRepository.save(newInvite);
        return newInvite;
    }

    /**
     *  Ändert den Status einer Invitation
     * @param happeningID  ID der Invitation die geändert werden soll
     * @param status    Neuer Invitationstatus als JSONObjekt
     * @return
     */
    @RequestMapping(value="/invitation/{happeningID}", method = RequestMethod.PUT)
    public @ResponseBody Invitation updateInvite(@PathVariable(value="happeningID") Long happeningID, @RequestBody InvitationStatus status ) {

        UserDetailsAdapter x = (UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User invitedUser = x.getUser();

        Happening e = happeningRepository.findOne(happeningID);

        if(e== null){
            throw new EventNotFoundException(happeningID.toString());
        }

        Invitation toUpdate = invitationRepository.findByInvitedUserAndHappening(invitedUser, e);

        if(toUpdate == null ){
            throw new InvitationNotFoundException("userID: " + invitedUser.getName() + " happening: " + e.getID());
        }

        toUpdate.setStatus(status);

        return invitationRepository.save(toUpdate);
    }

    /**
     *  Zeigt Invitations des eingeloggten users
     *  //TODO:geht nicht
     * @return
     */
    @RequestMapping(value="/invitation",method = RequestMethod.GET)
    public @ResponseBody List<Invitation> getInvites() {
        UserDetailsAdapter x = (UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User loggedIn = x.getUser();
        return invitationRepository.findByInvitedUser(loggedIn);
    }
}
