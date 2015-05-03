package com.gospry.api.presentation;

import com.gospry.api.domain.*;
import com.gospry.api.exception.EventNotFoundException;
import com.gospry.api.exception.GoogleNotificationServiceException;
import com.gospry.api.exception.InvitationNotFoundException;
import com.gospry.api.exception.NotAllowedException;
import com.gospry.api.service.HappeningRepository;
import com.gospry.api.service.InvitationRepository;
import com.gospry.api.service.UserRepository;
import com.gospry.api.service.notifications.IGoogleNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class InvitationController extends AbstractController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HappeningRepository happeningRepository;
    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private IGoogleNotificationService googleNotificationService;

    /**
     * Eine Person zu einem Event einladen
     *
     * @param invitedUser  User der eingeladen wird
     * @param happeningID  Event zu dem eingeladen wird
     * @param invitestatus Ein Invitationobjekt mit Invitationsstatus
     * @return
     */
    @RequestMapping(value = "/invitation/{happeningID}/{invitedUser}", method = RequestMethod.POST)
    public
    @ResponseBody
    Invitation invite(@PathVariable(value = "invitedUser") Long invitedUser,
                      @PathVariable(value = "happeningID") Long happeningID
            , @RequestBody Invitation invitestatus) {
        System.out.println("Inviting User" + Long.toString(invitedUser) + "to Happening" + Long.toString(happeningID));
        User invited;
        Happening happy;
        if (!happeningRepository.exists(happeningID)) {
            throw new EventNotFoundException("Happening does not exist " + happeningID);
        }

        happy = happeningRepository.findOne(happeningID);
        //Check if User is allowed to invite
        if (happy.getCreator().getUserID() != getCurrentUser().getUserID()) {
            Invitation temp = invitationRepository.findByInvitedUserAndHappening(getCurrentUser(), happy);
            if (temp == null || temp.isModerator() == false)
                throw new NotAllowedException("User not Creator or Moderator" + getCurrentUser().getUserID() + "of Happening " + happeningID);
        }
        if (!userRepository.exists(invitedUser)) {
            invited = new User();
            invited.setPhoneNumber(invitedUser);
            invited = userRepository.save(invited);
        } else {
            invited = userRepository.findByUserID(invitedUser);
        }
        //Check for previous invitation
        if (invitationRepository.findByInvitedUserAndHappening(invited, happy) != null) {
            return invitationRepository.findByInvitedUserAndHappening(invited, happy);
        }
        //TODO: Delete?! Check for previous invitation deprecated oder so siehe oben
 /*       for (Invitation temp : invited.getinvited_happenings()) {
            TODO: implement equals?
            if (temp.getHappening().equals(newInvite.getHappening())) {
                return temp;
            }
        }
        */

        Invitation newInvite = new Invitation();
        newInvite.setStatus(InvitationStatus.INVITED);
        newInvite.setInviter(getCurrentUser());
        newInvite.setHappening(happy);
        newInvite.setinvited_User(invited);
        newInvite = invitationRepository.save(newInvite);
        try {
            // invited = userRepository.save(invited);
            System.out.println("User invited" + invited.getUserID());
            // Check if user already registered Todo: if not then what?
            if (!invited.getGoogleauthenticationkey().isEmpty()) {
                log.info("Send google notification to User" + invited.getUserID() + "for Happening" + happeningID);
                googleNotificationService.createInviteNotifications(invited, happeningID);
            }

        } catch (Exception e) {
            System.out.println("Error inviting: " + e.toString());
        } catch (GoogleNotificationServiceException e) {
            log.warning("Not able to send google invitations to user" + invited);
        }
        //TODO:buggy wenn inviteduser= inviter ist...
        return newInvite;
    }

    /**
     * Ändert den Status einer Invitation
     *
     * @param happeningID ID der Invitation die geändert werden soll
     * @param status      Neuer Invitationstatus als JSONObjekt
     * @return
     */
    @RequestMapping(value = "/invitation/{happeningID}", method = RequestMethod.PUT)
    public
    @ResponseBody
    Invitation updateInvite(@PathVariable(value = "happeningID") Long happeningID, @RequestBody Invitation status) {
        //System.out.println("User" + Long.toString(getCurrentUser().getUserID()) + "responding to Happening" + Long.toString(happeningID) +"Status " +status2);
        Happening e = happeningRepository.findOne(happeningID);
        //InvitationStatus status = InvitationStatus.ATTENDING;
        if (e == null) {
            throw new EventNotFoundException(happeningID.toString());
        }
        Invitation toUpdate;
        if (e.getCreator().getUserID() == getCurrentUser().getUserID()) {
            toUpdate = null;
        } else {
            if (e.getIsPublic()) {
                toUpdate = new Invitation(getCurrentUser(), e.getCreator(), status.getStatus(), e);
            } else {
                toUpdate = invitationRepository.findByInvitedUserAndHappening(getCurrentUser(), e);
                if (toUpdate == null) {
                    throw new InvitationNotFoundException("userID: " + getCurrentUser().getName() + " happening: " + e.getID());
                }
                toUpdate.setStatus(status.getStatus());
                //TODO: Nicht schoen, vl Moderatoren doch besser in happening abspeichern
                if (status.isModerator() == true && getCurrentUser() == e.getCreator()) {
                    toUpdate.setIsModerator(true);
                }
            }
            toUpdate = invitationRepository.save(toUpdate);
        }

        return toUpdate;
    }

    /**
     * Zeigt Invitations des eingeloggten users
     * //TODO:geht nicht
     *
     * @return
     */
    @RequestMapping(value = "/invitation", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Invitation> getInvites() {

        return invitationRepository.findByInvitedUser(getCurrentUser());
    }

    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    public InvalidRequest defaultErrorHandler(HttpServletRequest request, Exception e) {
        return new InvalidRequest("not able to process request", request.getRequestURL().toString(), e.getClass());
    }
}
