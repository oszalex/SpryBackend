package com.gospry.api.service;

import com.gospry.api.domain.Location;
import com.gospry.api.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Long> {

    /**
     * Return an {@link User} instance with an email address.
     *
     * @param locationID
     * @return a {@link User instance} or null if not found.
     */
    Location findBylocid(Long locationID);

    Location findByName(String name);

}
