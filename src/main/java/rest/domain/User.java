package rest.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.keygen.StringKeyGenerator;

import javax.crypto.KeyGenerator;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

/**
 * Created by chris on 08.11.14.
 *
 * user registriert oder nur iwo eingeladen?!
 * token wo wird der gespechert
 * lazy/eager loading
 *
 * TODO: check cardinalities
 */

/**
 * undecided: picture, email
 */
@Entity
public class User {

    @javax.persistence.Id
    private long userID;
    @JsonIgnore
    private Date birth;
    @JsonIgnore
    private boolean isBored;
    @JsonIgnore
    private boolean isFemale;
    @JsonIgnore
    private String name;
    // add standard token

    @JsonIgnore
    static StringKeyGenerator generator = KeyGenerators.string();
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String token = "1234";

    @OneToMany
    private List<Happening> created_happenings;
    @OneToMany
    private List<Invitation> invited_happenings;
    //@ManyToOne

    //TODO:privacy? actuallity of location?
    //private Location location;



    public User(){
        password = generator.generateKey();
        //FIXME nur zum testen
        password  = "123456";
    }

   /* public List<Happening> getcreated_happenings()
    {
        return created_happenings;
    }
    public List<Invitation> getinvited_happenings()
    {
        return invited_happenings;
    }
    */
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
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

   /* public String toString()
    {
        return Long.toString(phoneNumber);
    }

    */
}
