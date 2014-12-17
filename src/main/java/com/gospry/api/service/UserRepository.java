package com.gospry.api.service;

import com.gospry.api.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Return an {@link User} instance with an email address.
     *
     * @param userID an email address as a String
     * @return a {@link User instance} or null if not found.
     */
    User findByUserID(Long userID);

}
