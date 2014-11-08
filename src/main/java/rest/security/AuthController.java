package rest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import rest.domain.User;
import rest.exception.UsernameAlreadyInUseException;
import rest.service.UserRepository;

import java.io.UnsupportedEncodingException;

/**
 * Created by chris on 08.11.14.
 *
 * TODO:
 * - limit activate attempts
 * - only one-time activation?
 */

@RestController
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    /**
     * curl http://localhost:8080/register/4369911602033
     *
     * TODO: test if user exists
     *
     * @return
     */
    @RequestMapping("/register/{phoneNumber}")
    public String registerUser(@PathVariable(value="phoneNumber") Long phoneNumber) {

        //create user
        User u = new User();
        u.setPhoneNumber(phoneNumber);

        try {
            userRepository.createAccount(u);
        } catch (UsernameAlreadyInUseException e) {
            e.printStackTrace();
            return "already registered";
        }

        //FIXME soll dann nicht mehr direkt übergeben werden!
        return u.getToken();
    }



    /**
     * curl http://localhost:8080/activate/4369911602022/1234
     *
     * @return
     */
    @RequestMapping("/activate/{phoneNumber}/{token}")
    public String activateUser(@PathVariable(value="phoneNumber") Long phoneNumber, @PathVariable(value="token") String token) {

        //find user
        User u = userRepository.findByPhoneNumber(phoneNumber);

        if(u != null){
            if (token.equals(u.getToken())) return u.getPassword();

            return "wrong token";
        }

        return "user not found";


    }
}