package com.gospry.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "invitation")
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false, unique = true)
    private long invitationId;

    //TODO:
    @ManyToOne
    //@OneToOne
    @JoinColumn(name = "invitedUser", referencedColumnName = "id")
    private User invitedUser;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "inviter", referencedColumnName = "id")
    private User inviter;
    @JsonIgnore
    private boolean isModerator = false;
    private InvitationStatus status;
    private Calendar createdAt;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "happening_id", referencedColumnName = "id")
    private Happening happening;

    public Invitation() {
    }

    public Invitation(User invited_User, User inviter, InvitationStatus status, Happening happy) {
        this.setinvited_User(invited_User);
        this.setInviter(inviter);
        this.setStatus(status);
        this.setHappening(happy);
    }

    public void setIsModerator(boolean isModerator) {
        this.isModerator = isModerator;
    }

    public boolean isModerator() {
        return isModerator;
    }

    public long getInvitationId() {
        return invitationId;
    }

    public long getHappeningId() {
        return happening.getID();
    }

    public long getInvitedUser() {
        return invitedUser.getUserID();
    }

    public void setinvited_User(User invited_User) {
        this.invitedUser = invited_User;
        if (!invited_User.getinvited_happenings().contains(this)) {
            invited_User.getinvited_happenings().add(this);
        }
    }

    public User getInviter() {
        return inviter;
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

    @Override
    public String toString() {
        return "(" + invitedUser.getUserID() + "|" + status.value() + "|" + isModerator() + ")";
    }


    @Override
    public boolean equals(Object h) {
        if (h == null) return false;

        if (h instanceof Invitation) {
            if (this.invitationId == ((Invitation) h).invitationId) return true;
        }

        return false;
    }
}
