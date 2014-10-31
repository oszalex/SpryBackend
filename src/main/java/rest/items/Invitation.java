package rest.items;


import javax.persistence.*;

/**
 * Created by chris on 31/10/14.
 */

@Entity
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int invitationId;
    private long userID;
    private long inviterID;
    private InvitationStatus status;
    @ManyToOne
    private Act act;

    public Invitation(){}

    public Invitation(long userID, long inviter, InvitationStatus status) {
        this.userID = userID;
        this.inviterID = inviter;
        this.status = status;
    }
}
