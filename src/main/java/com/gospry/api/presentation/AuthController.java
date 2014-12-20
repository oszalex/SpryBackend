package com.gospry.api.presentation;

import com.gospry.api.domain.PasswordObject;
import com.gospry.api.domain.User;
import com.gospry.api.exception.WrongTokenException;
import com.gospry.api.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

/**
 * TODO:
 * - limit activate attempts
 * - only one-time activation?
 */

@RestController
public class AuthController extends AbstractController {
    static Logger log = Logger.getLogger(AuthController.class.getName());

    @Autowired
    private UserRepository userRepository;

    /**
     * curl http://localhost:8080/register/4369911602033
     * <p/>
     * TODO: test if user exists
     *
     * @return
     */
    @RequestMapping(value = "/register/{phoneNumber}", method = RequestMethod.POST)
    public String registerUser(@PathVariable(value = "phoneNumber") Long phoneNumber) {
        User user = null;
        // String json = "APA91bFuklduxG3h6I9Bk1ek2lSUaBNgnLHK2WJrFrTLyeDf5CsvS8fr7P1A_Z4JGNiL5XmguCqBnIX-0HaQe3Us33ydjKrykA45Ak41gxiOd3RGAQgEO91GlsDptc1y9rlzfbsCjAZlvBq3f1zoQv06cCjemm99ZwqbVmqy9MDuSCaPXLOP4Qs";
        try {
            // JSONObject authID = new JSONObject(json) ;
        if (userRepository.exists(phoneNumber)) {
            user = userRepository.findByUserID(phoneNumber);
            //return userRepository.findByUserID(phoneNumber).getToken();
        } else {
            //create user
            user = new User();
            user.setPhoneNumber(phoneNumber);
        }
        //TODO: per SMS an die Nummer verschicken
            String json = "APA91bFuklduxG3h6I9Bk1ek2lSUaBNgnLHK2WJrFrTLyeDf5CsvS8fr7P1A_Z4JGNiL5XmguCqBnIX-0HaQe3Us33ydjKrykA45Ak41gxiOd3RGAQgEO91GlsDptc1y9rlzfbsCjAZlvBq3f1zoQv06cCjemm99ZwqbVmqy9MDuSCaPXLOP4Qs";
            //   System.out.println("AuthiD" + authID.getString("authID"));
            user.setgoogleID(json);
            user = userRepository.save(user);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return user.getToken();
    }


    /**
     * curl http://localhost:8080/activate/4369911602022/1234
     *
     * @return
     */
    @RequestMapping(value = "/activate/{phoneNumber}/{token}", method = RequestMethod.POST)
    public PasswordObject activateUser(@PathVariable(value = "phoneNumber") Long userID, @PathVariable(value = "token") String token) {
        User u = userRepository.findByUserID(userID);

        if (u != null) {
            if (token.equals(u.getToken())) return new PasswordObject(u.getPassword());
            else {
                //TODO: inform APP(errorcode) to reentry number
                log.warning("wrong token dude!");
                throw new WrongTokenException("wrong token");
            }
        } else {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * logout
     *
     * destroys session
     * @return empty string
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        request.getSession().invalidate();

        return new String();
    }
}