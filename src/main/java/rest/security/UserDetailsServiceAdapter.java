package rest.security;

/**
 * Created by chris on 08.11.14.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import rest.domain.User;
import rest.service.UserRepository;

/**
 * Adapts the {@link UserRepository} to the {@link UserDetailsService} interface
 * so Spring Security can use it as an authentication source.
 */
@Component
@Primary
public class UserDetailsServiceAdapter implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User account = userRepository.findByUserID(Long.valueOf(username));

        if (account == null) {
            throw new UsernameNotFoundException(username);
        }

        return new UserDetailsAdapter(account);
    }

}