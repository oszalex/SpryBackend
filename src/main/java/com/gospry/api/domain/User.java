package com.gospry.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.keygen.StringKeyGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by chris on 08.11.14.
 *
 * user registriert oder nur iwo eingeladen?!
 * token wo wird der gespechert
 * lazy/eager loading
 *
 * TODO:    check cardinalities, renew password some time(more than one device etc.)
 * TODO:privacy? actuallity of location?
 */

/**
 * undecided: picture, email
 */

@Entity
@Table(name="user")
public class User {

    static StringKeyGenerator generator = KeyGenerators.string();
    @Id
    @Column(name="ID", unique=true)
    private long userID;
    private Date birth;
    private boolean isBored;
    private boolean isFemale;
    private String name;
    private String password="";
    private String token;


    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "happening_id")
    private List<Happening> created_happenings;

    @OneToMany(fetch = FetchType.EAGER)
    //@OneToMany
    @JoinColumn(name = "invitation_id")
    private List<Invitation> invited_happenings;

    public User(){
        invited_happenings = new LinkedList<Invitation>();
        created_happenings = new LinkedList<Happening>();
    }

    public List<Invitation> getinvited_happenings()
    {
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

    public List<Happening> gethappenings()
    {
        //TODO:
        List<Happening> happys = created_happenings;
        for(Invitation invitation : invited_happenings )
        {
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
        if(password.equals("")) {
            password = generator.generateKey();
            //TODO: nur zum testen
            password  = "123456";
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
        token = "1234";
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object x)
    {
        if(x == null) return false;
        if(!(x instanceof User)) return false;
        if(!(((User) x).getUserID() == this.getUserID())) return false;
        else return true;
    }
}
