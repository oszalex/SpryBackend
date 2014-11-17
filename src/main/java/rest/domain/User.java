package rest.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javassist.expr.Instanceof;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.keygen.StringKeyGenerator;

import javax.crypto.KeyGenerator;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
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
public class User {
    @javax.persistence.Id
    private long userID;
    private Date birth;
    private boolean isBored;
    private boolean isFemale;
    private String name;
    static StringKeyGenerator generator = KeyGenerators.string();
    private String password="";
    private String token;
    @OneToMany
    private List<Happening> created_happenings;
    @OneToMany(mappedBy="", fetch=FetchType.EAGER)
    private List<Invitation> invited_happenings;

    public User(){
        invited_happenings = new LinkedList<Invitation>();
        created_happenings = new LinkedList<Happening>();
    }
    @JsonIgnore
    public List<Invitation> getinvited_happenings()
    {
        return invited_happenings;
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
