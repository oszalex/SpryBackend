package com.gospry.api.domain;

import javax.persistence.*;

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", unique = true)
    private long locid;

    private double longitude;
    private double latitude;
    private String name;
    private String description;

    // @JsonIgnore
    // @OneToMany(fetch = FetchType.EAGER, mappedBy = "happening")
    //@JoinColumn(name = "happening_id")
    // private List<Happening> happenings;
    // is this location a public one?
    private boolean publiclocation;

    public Location() {
        //happenings = new LinkedList<Happening>();
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getPubliclocation() {
        return publiclocation;
    }

    public void setPubliclocation(boolean is_Public) {
        this.publiclocation = is_Public;
    }

    @Override
    public String toString() {
        return getName();
    }

/*    public long getlocid() {
        return locid;
    }*/
}
