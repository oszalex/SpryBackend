package com.gospry.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity
public class Happening {
    private final static TimeZone tz_gmt = TimeZone.getTimeZone("GMT");

    // unique happening id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;

    // invited people
    @JsonIgnore
    @OneToMany(targetEntity= Invitation.class, mappedBy="happening", fetch=FetchType.EAGER)
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

    // location
    @ManyToOne
    private Location location;

    //happening creator
    @JsonIgnore
    @ManyToOne  //(targetEntity=User.class, fetch=FetchType.EAGER)
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
    public void setCreator(User creator) {
        this.creator  = creator;
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

    public boolean getIsPublic() {
        return isPublic;
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

    public char[] getDescription() {
        return description;
    }

    public void setDescription(char[] description) {
        this.description = description;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
}
