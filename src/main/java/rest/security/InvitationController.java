package rest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import rest.domain.Happening;
import rest.domain.Invitation;
import rest.domain.InvitationStatus;
import rest.domain.User;
import rest.exception.EventNotFoundException;
import rest.exception.InviteAlreadyExists;
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

    @RequestMapping(value="/invitation/{happeningID}/{invitedUser}",method = RequestMethod.POST)
    public @ResponseBody Invitation invite(@PathVariable(value="invitedUser") Long invitedUser,
                                                 @PathVariable(value="happeningID") Long happeningID,
                                                 @RequestBody Invitation newInvite) {
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
        newInvite.setHappening(happeningRepository.findOne(happeningID));
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




  /*  @RequestMapping(value="/invitation",method = RequestMethod.POST)
    public @ResponseBody Invitation invite2(@RequestBody(value="invited") String invited_User,
                                            @RequestBody(value="invited_UserID") long invited_UserID,
                                            @RequestBody(value="happening_ID") long happening_ID,
                                            @RequestBody(value="status")InvitationStatus status) {
        if(!happeningRepository.exists((happening_ID))){
            throw new EventNotFoundException(happening_ID + " Does not exist");
        }
        UserDetailsAdapter x = (UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User inviter = x.getUser();
        User invited;
        if (!userRepository.exists(invited_UserID)) {
            invited = new User();
            invited.setPhoneNumber(invited_UserID);
            userRepository.save(invited);
        } else {
            invited = userRepository.findByUserID(invited_UserID);
        }
        Invitation newInvite = new Invitation(invited,inviter,status, happeningRepository.findOne(happening_ID));
        invitationRepository.save(newInvite);
        return newInvite;

    }*/
}
