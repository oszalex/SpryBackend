package rest.service;

import rest.domain.User;

/**
 * Created by chris on 08.11.14.
 */
public interface UserRepository {

    /**
     * Return an {@link User} instance with an email address.
     * @param phoneNumber an email address as a String
     * @return a {@link User instance} or null if not found.
     */
    User findByPhoneNumber(Long phoneNumber);

}
