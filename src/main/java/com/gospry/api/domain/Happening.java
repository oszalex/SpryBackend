package com.gospry.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "happening")
public class Happening {
    private final static TimeZone tz_gmt = TimeZone.getTimeZone("GMT");
    // time of happening creation
    private Calendar createdAt = new GregorianCalendar(tz_gmt);

    // invited people
    // start time of event
    private Calendar start_time = new GregorianCalendar(tz_gmt);
    // unique happening id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private long Id;
    /**
     * wenn ein happening gelöscht wird, sollen auch alle invitations gelöscht werden dh cascade all
     */
    @JsonIgnore
    @OneToMany(targetEntity = Invitation.class, mappedBy = "happening", fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Invitation> invitations;
    // Dauer
    private int duration = 120;
    // person limit
    private int max_attending = Integer.MAX_VALUE;

    private int min_attending = 0;

    // price euro
    private double price_euro;

    // private description
    // only for event agencies
    private String description = new String("");

    // TODO: location - this is a workaround
    /* @ManyToOne
    @JoinColumn(name="location_id", referencedColumnName="ID")
    private Location location;
    */
    private String location;
    //happening creator
    @JsonIgnore
    @ManyToOne  //(targetEntity=User.class, fetch=FetchType.EAGER)
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private User creator;

    // is this a public
    private boolean isPublic;


    private ArrayList<String> keywords = new ArrayList<String>();

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(ArrayList<String> keywords) {
        this.keywords = keywords;
    }

    public long getID() {
        return Id;
    }

    public long getcreatorID() {
        return creator.getUserID();
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

    public Calendar getStart_time() {
        return start_time;
    }

    public Calendar getCreatedAt() {
        return createdAt;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public int getMax_attending() {
        return max_attending;
    }

    public void setMax_attending(int max_attending) {
        this.max_attending = max_attending;
    }

    public int getMin_attending() {
        return min_attending;
    }

    public void setMin_attending(int min_attending) {
        this.min_attending = min_attending;
    }

    public double getPrice_euro() {
        return price_euro;
    }

    public void setPrice_euro(double price_euro) {
        this.price_euro = price_euro;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
