package rest.domain;


import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by chris on 31/10/14.
 */

@Entity
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int invitationId;

    @ManyToOne
    private User user;

    @OneToOne
    private User inviter;

    private InvitationStatus status;

    private Calendar createdAt;

    @ManyToOne
    private Happening happening;

    public Invitation(){}

    public Invitation(User user, User inviter, InvitationStatus status) {
        this.user = user;
        this.inviter = inviter;
        this.status = status;
    }

    public int getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(int invitationId) {
        this.invitationId = invitationId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Calendar getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Calendar createdAt) {
        this.createdAt = createdAt;
    }

    public Happening getHappening() {
        return happening;
    }

    public void setHappening(Happening happening) {
        this.happening = happening;
    }
}
