package rest.security;

import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import rest.domain.Happening;
import rest.domain.Invitation;
import rest.domain.InvitationStatus;
import rest.domain.User;
import rest.exception.EventNotFoundException;
import rest.exception.InvitationNotFoundException;
import rest.exception.InviteAlreadyExists;
import rest.exception.NotAllowedException;
import rest.service.HappeningRepository;
import rest.service.InvitationRepository;
import rest.service.UserRepository;

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
    public @ResponseBody Invitation invite(@PathVariable(value="invitedUser") Long invitedUser,
                                                 @PathVariable(value="happeningID") Long happeningID){
                                               //  ,@RequestBody Invitation invitestatus) {
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
        if(!happy.getCreator().equals(x.getUser()))
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
     * @param inviteID  ID der Invitation die geändert werden soll
     * @param status    Neuer Invitationstatus als JSONObjekt
     * @return
     */
    @RequestMapping(value="/invitation/{inviteID}/",method = RequestMethod.PUT)
    public @ResponseBody Invitation updateInvite(@PathVariable(value="inviteID") Long inviteID,@RequestBody Invitation status ) {

        if(!invitationRepository.exists(inviteID))
            throw new InvitationNotFoundException(inviteID.toString() + " Does not exist");
        UserDetailsAdapter x = (UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Invitation update = invitationRepository.findOne(inviteID);
        if(x.getUser().equals(update.getinvited_User())){
            update.setStatus(status.getStatus());
            invitationRepository.save(update);
        }
        else
        {
            throw new NotAllowedException("Wrong User?");
        }
        return invitationRepository.findOne(inviteID);
    }

    /**
     *  Zeigt Invitations des eingeloggten users
     *  //TODO:geht nicht
     * @return
     */
    @RequestMapping(value="/invitation/",method = RequestMethod.GET)
    public @ResponseBody List<Invitation> getInvites() {
        UserDetailsAdapter x = (UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User loggedIn = x.getUser();
        return loggedIn.getinvited_happenings();
    }
}
