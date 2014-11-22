package com.gospry.api;

import com.gospry.api.service.HappeningRepository;
import com.gospry.api.service.InvitationRepository;
import com.gospry.api.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

@Configuration
public class SeedConfig {

    @Autowired
    UserRepository userRepository;

    @Autowired
    HappeningRepository happeningRepository;

    @Autowired
    InvitationRepository invitationRepository;

    @Bean
    public ApplicationListener<ContextRefreshedEvent> contextInitFinishListener() {
        return new SeedListener(userRepository, happeningRepository, invitationRepository);
    }
}