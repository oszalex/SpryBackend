package rest.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by chris on 31/10/14.
 */

@Entity
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long invitationId;
    @JsonIgnore
    @ManyToOne
    private User invited_User;
    @JsonIgnore
    @OneToOne
    private User inviter;

    private InvitationStatus status;

    private Calendar createdAt;

    @JsonIgnore
    @ManyToOne
    private Happening happening;

    public Invitation(){}

    public Invitation(User invited_User, User inviter, InvitationStatus status, Happening happy) {
        this.setinvited_User(invited_User);
        this.setInviter(inviter);
        this.setStatus(status);
        this.setHappening(happy);
    }

    public long getInvitationId() {
        return invitationId;
    }

    public User getinvited_User() {
        return invited_User;
    }

    public void setinvited_User(User invited_User) {
        this.invited_User = invited_User;
        if (!invited_User.getinvited_happenings().contains(this)) {
            invited_User.getinvited_happenings().add(this);
        }
    }

    public User getInviter() {
        return inviter;
    }
    public long getinviterID() {
        return inviter.getUserID();
    }
    public long getinvited_UserID() {
        return invited_User.getUserID();
    }
    public void setInviter(User inviter) {
        this.inviter = inviter;
    }

    public InvitationStatus getStatus() {
        return status;
    }

    public void setStatus(InvitationStatus status) {
        this.status = status;
    }

    public void setCreatedAt(Calendar createdAt) {
        this.createdAt = createdAt;
    }

    public Happening getHappening() {
        return happening;
    }

    public void setHappening(Happening happening) {
        this.happening = happening;
      /*  if (!happening.getInvitations().contains(this)) {
            happening.getInvitations().add(this);
        }*/
    }
}
