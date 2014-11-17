package rest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import rest.domain.Happening;
import rest.domain.Invitation;
import rest.domain.InvitationStatus;
import rest.domain.User;
import rest.exception.EventNotFoundException;
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

    @RequestMapping(value="/invitation/{invitedUser}",method = RequestMethod.POST)
    public @ResponseBody Invitation invite(@PathVariable("invitedUser") long invitedUser,
                                           @RequestBody Invitation newInvite) {
        UserDetailsAdapter x = (UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newInvite.setInviter(x.getUser());
        User invited;
        if (!userRepository.exists(invitedUser)) {
            invited = new User();
            invited.setPhoneNumber(invitedUser);
            userRepository.save(invited);
        } else {
            invited = userRepository.findByUserID(invitedUser);
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
