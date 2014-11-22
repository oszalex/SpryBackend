package com.gospry.api;

import com.gospry.api.domain.Happening;
import com.gospry.api.domain.Invitation;
import com.gospry.api.domain.InvitationStatus;
import com.gospry.api.domain.User;
import com.gospry.api.service.HappeningRepository;
import com.gospry.api.service.InvitationRepository;
import com.gospry.api.service.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;


public class SeedListener implements ApplicationListener<ContextRefreshedEvent> {

    private UserRepository userRepository;
    private HappeningRepository happeningRepository;
    private InvitationRepository invitationRepository;

    public SeedListener(UserRepository ur, HappeningRepository hr, InvitationRepository ir) {
        this.userRepository = ur;
        this.happeningRepository = hr;
        this.invitationRepository = ir;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //populate database with required values, using UserRepository

        User chris = new User();
        chris.setPhoneNumber(4369911602033L);
        User alex = new User();
        alex.setPhoneNumber(4369911602033L);

        chris = userRepository.save(chris);
        alex = userRepository.save(alex);

        Happening some_public_event = new Happening();
        some_public_event.setCreator(alex);
        some_public_event.setDuration(120);
        some_public_event.setIsPublic(true);

        Happening some_private_event = new Happening();
        some_private_event.setCreator(alex);
        some_private_event.setDuration(120);
        some_private_event.setIsPublic(false);

        some_public_event = happeningRepository.save(some_public_event);
        some_private_event = happeningRepository.save(some_private_event);

        Invitation chris_to_alex = new Invitation(chris, alex, InvitationStatus.ATTENDING,some_private_event);
        invitationRepository.save(chris_to_alex);
    }
}