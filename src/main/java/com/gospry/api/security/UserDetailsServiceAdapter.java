package com.gospry.api.security;

import com.gospry.api.domain.User;
import com.gospry.api.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

/**
 * Adapts the {@link UserRepository} to the {@link UserDetailsService} interface
 * so Spring Security can use it as an authentication source.
 */
@Service
@Transactional(readOnly = true)
public class UserDetailsServiceAdapter implements UserDetailsService {
    static Logger log = Logger.getLogger(UserDetailsServiceAdapter.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User account = userRepository.findByUserID(Long.valueOf(username));

        if (account == null) {
            log.warning("User tried to log in: " + username);
            throw new UsernameNotFoundException(username);
        }

        return new UserDetailsAdapter(account);
    }

}