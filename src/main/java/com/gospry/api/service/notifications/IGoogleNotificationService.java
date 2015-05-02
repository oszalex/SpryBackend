package com.gospry.api.service.notifications;

import com.gospry.api.domain.User;
import com.gospry.api.exception.GoogleNotificationServiceException;

/**
 * Created by chris on 25/04/15.
 */
public interface IGoogleNotificationService {
    void createInviteNotifications(User user, long happeningId) throws GoogleNotificationServiceException;
    String subscribe(String authid, User user) throws GoogleNotificationServiceException;
}
