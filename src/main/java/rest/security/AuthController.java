package rest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import rest.domain.User;
import rest.service.UserRepository;

import java.io.UnsupportedEncodingException;

/**
 * Created by chris on 08.11.14.
 */

@RestController
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    /**
     * curl --user 4369911602033:1234 http://localhost:8080/register
     *
     * TODO: test if user exists
     *
     * @return
     */
    @RequestMapping("/register")
    public String registerUser(@RequestHeader("Authorization") String credentials) {

        if ((credentials != null) && credentials.startsWith("Basic ")) {
            byte[] base64Token = new byte[0];
            try {
                base64Token = credentials.substring(6).getBytes("UTF-8");
                String token = new String(Base64.decode(base64Token));
                String username = "";
                //String password = "";

                int delim = token.indexOf(":");
                if (delim != -1) {
                    username = token.substring(0, delim);
                    //password = token.substring(delim + 1);
                }

                //create user
                User u = new User();

                //FIXME soll dann nicht mehr direkt übergeben werden!
                return u.getToken();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return "could not encode header";
        }

        return "no valid auth header";
    }



    /**
     * curl --user 4369911602033:1234 http://localhost:8080/register
     *
     * @param credentials
     * @return
     */
    @RequestMapping("/activate/{token}")
    public String activateUser(@RequestHeader("Authorization") String credentials, @PathVariable(value="token") String token) {

        //TODO: test authheader

        if ((credentials != null) && credentials.startsWith("Basic ")) {
            byte[] base64Token = new byte[0];
            try {
                base64Token = credentials.substring(6).getBytes("UTF-8");
                String h_token = new String(Base64.decode(base64Token));
                String username = "";

                int delim = h_token.indexOf(":");

                if (delim != -1) {
                    username = h_token.substring(0, delim);

                    Long phoneNumber = Long.parseLong(username, 10);

                    System.out.print("Number: " + phoneNumber);

                    //find user
                    User u = userRepository.findByPhoneNumber(phoneNumber);

                    if(u != null){
                        if (token.equals(u.getToken())) return "pw:" + u.getPassword();

                        return "wrong token";
                    }

                    return "user not found";
                }

                return "not valid auth header";

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return "could not encode header";
        }

        return "no valid auth header provided";
    }
}