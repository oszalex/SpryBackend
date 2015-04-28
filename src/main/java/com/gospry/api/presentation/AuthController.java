package com.gospry.api.presentation;

import com.gospry.api.domain.InvalidRequest;
import com.gospry.api.domain.PasswordObject;
import com.gospry.api.domain.User;
import com.gospry.api.domain.UserRegistration;
import com.gospry.api.exception.InvalidRequestException;
import com.gospry.api.exception.WrongTokenException;
import com.gospry.api.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
     * <p/>
     * ATTENTION: this request must have a non-empty body! if there is no requestbody
     * this route will not be used.
     *
     * @return
     */


    @RequestMapping(value = "/register/{phoneNumber}", method = RequestMethod.POST)
    public String registerUser(
            @RequestBody UserRegistration data,
            @PathVariable(value = "phoneNumber") Long phoneNumber) throws InvalidRequestException{

        // 1: update or create user
        User user;

        if (userRepository.exists(phoneNumber)) {
            log.info("there is already a user with this phoneNumber");
            //find user
            user = userRepository.findByUserID(phoneNumber);
        } else {
            log.info("create new user with id: " + phoneNumber);
            //create user
            user = new User();
            user.setPhoneNumber(phoneNumber);
        }

        // 2: @todo random token
        user.setToken("1234");
        if(data.getAuthID() != null) user.setgoogleID(data.getAuthID());
        user = userRepository.save(user);

        // 3: @todo send token per sms

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
     * <p/>
     * destroys session
     *
     * @return empty string
     */

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        request.getSession().invalidate();

        return new String();
    }


    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    public InvalidRequest defaultErrorHandler(HttpServletRequest request, Exception e) {
        return new InvalidRequest("not able to process request", request.getRequestURL().toString(), e.getClass());
    }
}