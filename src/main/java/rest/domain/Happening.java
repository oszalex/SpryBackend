package rest.domain;

import javax.persistence.*;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by chris on 31/10/14.
 */

@Entity
public class Happening {
    private final static TimeZone tz_gmt = TimeZone.getTimeZone("GMT");
    private final static Pattern location_pattern = Pattern.compile("@(\\w+)");
    private final static Pattern number_pattern = Pattern.compile("\\+(\\d+)");


    // unique happening id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;

    // invited people
    @OneToMany(targetEntity=Invitation.class, mappedBy="happening", fetch=FetchType.EAGER)
    private List<Invitation> invitations;

    // Dauer
    private int duration = 120;

    // time of happening creation
    private Calendar createdAt = new GregorianCalendar(tz_gmt);

    // start time of event
    private Calendar start_time = new GregorianCalendar(tz_gmt);

    // person limit
    private int max_attending = Integer.MAX_VALUE;

    private int min_attending = 0;

    // price euro
    private double price_euro;

    // private description
    // only for event agencies
    private char description[] = new char[140];

    // locaton
    //change fetch type @OneToMany(fetch=FetchType.EAGER)
    @ManyToOne
    private Location location;

    //happening creator
    @ManyToOne
    private User creator;

    // is this a public
    private boolean isPublic;

    private ArrayList<String> keywords = new ArrayList<String>();

    /*public String getCreator(){
        return creator.toString();
    }
    */
    public void setCreator(User creator) {
        this.creator  = creator;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<Invitation> getInvitations() {
        return invitations;
    }

    public Calendar getCreatedAt() {
        return createdAt;
    }

    public boolean isPublic() {
        return isPublic;
    }
}
