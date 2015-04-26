package com.gospry.api.service.notifications.impl;

import com.gospry.api.domain.User;
import com.gospry.api.exception.GoogleNotificationServiceException;
import com.gospry.api.service.notifications.IGoogleNotificationService;
import com.gospry.api.service.notifications.IGoogleRequest;
import com.gospry.api.service.notifications.impl.domain.GoogleAddRegistrationRequest;
import com.gospry.api.service.notifications.impl.domain.GoogleCreateRegistrationRequest;
import com.gospry.api.service.notifications.impl.domain.GoogleNotificationRequest;
import com.gospry.api.service.notifications.impl.domain.GoogleRegistrationRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * GoogleNotificationService
 *
 * This service allows subscribing to google mobile notifications
 *
 * Created by chris on 25/04/15.
 */

@Service
public class GoogleNotificationService implements IGoogleNotificationService {
    private static final Logger log = Logger.getLogger(GoogleNotificationService.class.getName());

    @Value("${google.notifications.urls.notify}")
    private String url_notify;
    @Value("${google.notifications.urls.register}")
    private String url_register;
    @Value("${google.notifications.authKey}")
    private String authKey;
    @Value("${google.notifications.project_id}")
    private String project_id = "343359709951";

    /**
     *
     * @param authid google specific identifier
     * @param user user who wants to receive google notification
     * @return notification key
     * @throws GoogleNotificationServiceException
     */
    @Override
    public String subscribe(String authid, User user) throws GoogleNotificationServiceException {
        GoogleRegistrationRequest registrationRequest;
        log.fine("create new google notification subscription for user: " + user.getUserID());

        // create or just add a new key regarding existing notification key
        if(user.getGoogleauthenticationkey().isEmpty())
            registrationRequest = new GoogleCreateRegistrationRequest(authid, user.getUserID());
        else
            registrationRequest = new GoogleAddRegistrationRequest(authid, user.getGoogleauthenticationkey());

        try {
            String response = sendToGoogle(registrationRequest, url_register, true);
            JSONObject result = new JSONObject(response);

            if (result.has("error")) {
                log.warning("not able to create notification abo. received error from google: "
                        + result.get("error"));

                throw new GoogleNotificationServiceException();
            }

            if(! result.has("notification_key")){
                log.warning("google response should contain a notification key, which is missing!");
                throw new GoogleNotificationServiceException();
            }

           return (String) result.get("notification_key");

        } catch (IOException e) {
            log.warning("network error: could not connect to google, received IOException: " + e.getMessage());
            throw new GoogleNotificationServiceException();
        } catch (JSONException e) {
            log.warning("not able to decode google json response: " + e.getMessage());
            throw new GoogleNotificationServiceException();
        }
    }


    /**
     * send request to google notification service
     *
     * @param request
     * @param url
     * @param withProjectId
     * @return
     * @throws IOException
     */
    private String sendToGoogle(IGoogleRequest request, String url, boolean withProjectId) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);

        // set header
        httpPost.addHeader("Authorization", "key=" + authKey);
        if(withProjectId) httpPost.addHeader("project_id", project_id);

        // set body
        httpPost.setEntity(new StringEntity(request.toJson(),
                ContentType.create("application/json", "UTF-8")));

        // execute request
        log.fine("send google notification request " + httpPost.toString());

        HttpResponse response = client.execute(httpPost);
        String res = EntityUtils.toString(response.getEntity());

        log.fine("received google notification response: " + res);

        return res;
    }

    /**
     * create and send google invitation notifications
     *
     * @param user
     * @param happeningId
     * @throws GoogleNotificationServiceException
     */
    @Override
    public void createInviteNotifications(User user, long happeningId) throws GoogleNotificationServiceException {
        GoogleNotificationRequest request = new GoogleNotificationRequest(
                user.getGoogleauthenticationkey(),
                user.getGoogleauthenticationids(),
                happeningId);

        log.fine(String.format("create new google invite notification for user %d and happening %d",
                user.getUserID(),
                happeningId));

        try {
            sendToGoogle(request, url_notify, false);
        } catch (IOException e) {
            log.warning("network error: could not send request to google: " + e.getMessage());
            throw new GoogleNotificationServiceException();
        }
    }

}