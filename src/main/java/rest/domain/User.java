package rest.domain;

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
    private long phoneNumber;
    private boolean isFemale;
    private String password;
    private String name;
    // add standard token
    private String token = "1234";
    //TODO:privacy? actuallity of location?

    static StringKeyGenerator generator = KeyGenerators.string();

    //@ManyToOne
    //private Location location;
    private Date birth;
    private boolean isBored;

    @OneToMany
    private List<Happening> created_happenings;

    @OneToMany
    private List<Invitation> invited_happenings;

    public User(){
        password = generator.generateKey();
    }


    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phone_number) {
        this.phoneNumber = phone_number;
    }

    public boolean isFemale() {
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

    public boolean isBored() {
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
}
