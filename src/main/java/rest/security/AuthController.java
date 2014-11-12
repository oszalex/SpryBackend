package rest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rest.domain.PasswordObject;
import rest.domain.User;
import rest.exception.UsernameAlreadyInUseException;
import rest.exception.WrongTokenException;
import rest.service.UserRepository;

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
        //TODO: Wenn  User automatisch durch invite angelegt wurde ==> Fehler
        if(userRepository.exists(phoneNumber) )
            throw new UsernameAlreadyInUseException(phoneNumber.toString() + " already exists");

        //create user
        User u = new User();
        u.setPhoneNumber(phoneNumber);
        userRepository.save(u);

        //FIXME soll dann nicht mehr direkt übergeben werden!
        return u.getToken();
    }



    /**
     * curl http://localhost:8080/activate/4369911602022/1234
     *
     * @return
     */
    @RequestMapping("/activate/{phoneNumber}/{token}")
    public PasswordObject activateUser(@PathVariable(value="phoneNumber") Long userID, @PathVariable(value="token") String token) {

        //find user
        User u = userRepository.findByUserID(userID);

        if(u != null){
            if (token.equals(u.getToken())) return new PasswordObject(u.getPassword());

            throw new WrongTokenException("wrong token");
        }

        throw new ResourceNotFoundException();
    }
}