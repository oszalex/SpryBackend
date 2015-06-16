package com.gospry.api.domain;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Location {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;

    private double longitude;
    private double latitude;
    private String name;
    private String description;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "happening_id")

    private List<Happening> happenings;
    // is this location a public one?
    private boolean isPublic;

    public Location() {
        happenings = new LinkedList<Happening>();
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

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    @Override
    public String toString() {
        return getName();
    }
}
