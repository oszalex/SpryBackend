package rest.security;

/**
 * Created by chris on 08.11.14.
 */

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import rest.domain.User;

/**
 * Adapts the {@link User} to the {@link UserDetails}.
 * This way the {@link User} can be used for core user information.
 */
public class UserDetailsAdapter implements UserDetails {

    private User user;

    public UserDetailsAdapter(User user) {
        this.user = user;
    }

    @Override
    public String getUsername() {
        return String.valueOf(user.getUserID());
    }

    public User getUser()
    {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //TODO: for registration
    @Override
    public boolean isEnabled() {
        return true;
    }
}