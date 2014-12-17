package com.gospry.api.service;

import com.gospry.api.domain.Happening;
import com.gospry.api.domain.Invitation;
import com.gospry.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    //List<Invitation> findByHappenings(@Param("happening") Collection<Happening> happenings);
    //List<Invitation> findByUser()

    Invitation findByInvitedUserAndHappening(User invitedUser, Happening happening);

    List<Invitation> findByInvitedUser(User invitedUser);
}
