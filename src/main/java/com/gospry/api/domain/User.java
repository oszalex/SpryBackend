package com.gospry.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gospry.api.exception.GoogleNotificationServiceException;
import com.gospry.api.service.notifications.IGoogleNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.keygen.StringKeyGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by chris on 08.11.14.
 * <p/>
 * user registriert oder nur iwo eingeladen?!
 * token wo wird der gespechert
 * lazy/eager loading
 * <p/>
 * TODO:    check cardinalities, renew password some time(more than one device etc.)
 * TODO:privacy? actuallity of location?
 * <p/>
 * TODO:
 * - rename Methods camelstyle!
 * - sex can be undefined. use Boolean instead? (null possible and default)
 * - encrypt passwords
 */

/**
 * undecided: picture, email
 */

@Entity
@Table(name = "user")
public class User {

    static StringKeyGenerator generator = KeyGenerators.string();
    @Id
    @Column(name = "ID", unique = true)
    private long userID;
    private Date birth;
    private boolean isBored;
    private boolean isFemale;
    private String name;
    private String password = "";
    private String token;
    //  @ElementCollection
    private ArrayList<String> googleauthenticationids = new ArrayList<String>();
    private String googleauthenticationkey = "";
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "happening_id")
    private List<Happening> created_happenings;
    @OneToMany(fetch = FetchType.EAGER)
    //@OneToMany
    @JoinColumn(name = "invitation_id")
    private List<Invitation> invited_happenings;

    //please do not use this in this layer... move logic to service layer
    @Transient
    @Autowired
    private IGoogleNotificationService googleNotificationService;

    //TODO: Implement Friends
    // @ManyToMany(fetch = FetchType.EAGER)
    // @JoinColumn(name = "id")
    //private List<User> friends;


    public User() {
        invited_happenings = new LinkedList<Invitation>();
        created_happenings = new LinkedList<Happening>();
        googleauthenticationids = new ArrayList<String>();
    }

    public ArrayList<String> getGoogleauthenticationids() {
        return googleauthenticationids;
    }

    public String getGoogleauthenticationkey() {
        return googleauthenticationkey;
    }

    public void setGoogleauthenticationkey(String googleauthenticationkey) {
        this.googleauthenticationkey = googleauthenticationkey;
    }

    public void setgoogleID(String authid) {
        if (!googleauthenticationids.contains(authid)) {
            //TODO: refactor! do not call service in domain!
            try {
                googleauthenticationkey = googleNotificationService.subscribe(authid, this);
            } catch (GoogleNotificationServiceException e) {
                // TODO:
                // do nothing. error reporting should be done in service layer
                // therefore move this logic to service layer
            }
            googleauthenticationids.add(authid);
        }
    }


    public List<Invitation> getinvited_happenings() {
        return invited_happenings;
    }

    public void addcreatedHappening(Happening newHappening) {
        created_happenings.add(newHappening);
    }

    //TODO: schöner machen Sets anstatt lists verwenden?
    public void addinvitation(Invitation invite) {
        for (Invitation temp : invited_happenings) {
            if (temp.getHappening().equals(invite.getHappening())) {
                return;
            }
        }
        invited_happenings.add(invite);
    }

    public List<Happening> gethappenings() {
        //TODO:
        List<Happening> happys = created_happenings;
        for (Invitation invitation : invited_happenings) {
            happys.add(invitation.getHappening());
        }
        return happys;
    }

    @JsonIgnore
    public long getUserID() {
        return userID;
    }

    public void setPhoneNumber(long phone_number) {
        this.userID = phone_number;
    }

    public boolean getIsFemale() {
        return isFemale;
    }

    public void setFemale(boolean isFemale) {
        this.isFemale = isFemale;
    }

    public String getPassword() {
        if (password.equals("")) {
            password = generator.generateKey();
            //TODO: nur zum testen
            password = "123456";
        }
        return password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public boolean getIsBored() {
        return isBored;
    }

    public void setBored(boolean isBored) {
        this.isBored = isBored;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object user) {
        if (user == null) return false;

        if (user instanceof User) {
            if (this.getUserID() == ((User) user).getUserID()) return true;
        }

        return false;
    }
}
