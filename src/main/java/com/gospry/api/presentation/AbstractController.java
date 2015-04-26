package com.gospry.api.presentation;

import com.gospry.api.domain.User;
import com.gospry.api.security.UserDetailsAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;


public abstract class AbstractController {
    static final Logger log = Logger.getLogger(AbstractController.class.getName());

    @Autowired(required = true)
    protected HttpServletRequest request;

    private User currentUser;

    /**
     * get logged in user
     *
     * @return current user
     */
    public User getCurrentUser() {
     /*   if (currentUser != null) {
            return currentUser;
        }
        */
        UserDetailsAdapter adapter = (UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        currentUser = adapter.getUser();

        return currentUser;
    }
}
