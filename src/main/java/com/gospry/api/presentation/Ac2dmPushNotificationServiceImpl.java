package com.gospry.api.presentation;

import com.gospry.api.domain.User;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * Created by Alex on 19.12.2014.
 */
public class Ac2dmPushNotificationServiceImpl {
    private final static String authKey = "AIzaSyBLN-1EiniZ9aI-1rm4XcZNaC9-x6qu8_w";
    private final static String project_id = "64541343883";
    private String sendingRoleAccount;
    private String sendingRolePassword;

    public static String createNotificationUser(String authid, User user) {
        String authID = "";
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost("http://android.googleapis.com/gcm/notification");
            //  httpPost.addHeader("Authorization", "key=" + authKey);
            // httpPost.addHeader("project_id", project_id);
            //   httpPost.addHeader("content-type", "application/json");

            JSONObject jason = new JSONObject();
            if (user.getGoogleauthenticationkey().equals("")) {
                jason.append("operation", "create");
            } else {
                jason.append("operation", "add");
                jason.append("notification_key", user.getGoogleauthenticationkey());
            }
            jason.append("notification_key_name", user.getUserID());
            jason.append("registration_ids", authid);
            StringEntity stringEntity = new StringEntity(
                    JSON_STRING,
                    "application/json",
                    "UTF-8");
            httpPost.setEntity(stringEntity);

            HttpResponse response = client.execute(httpPost);

            System.out.println(response.toString());
            HttpEntity entity = response.getEntity();
            String resp = EntityUtils.toString(entity);
            //   JSONObject result = new JSONObject(resp);
            //TODO: Errorhandling
            //   authID = (String) result.get("notification_key");
        } catch (Exception e) {
            //TODO:Errorhandling
            System.out.println("hier2" + e.toString());
        }
        return authID;
    }

    /** request:
     {
   "operation": "add",
   "notification_key_name": "appUser-Chris",
   "notification_key": "aUniqueKey"
   "registration_ids": ["4", "8", "15", "16", "23", "42"]
}
*/

    private static void sendNotification(User user, List<String> payload) throws HttpException, IOException {
        String authID = "";
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost("https://android.googleapis.com/gcm/notification");
            httpPost.addHeader("Authorization", "key=" + authKey);
            httpPost.addHeader("project_id", project_id);
            httpPost.addHeader("Content-Type", "application/json");

            // JSONObject jason = new JSONObject();
            //  jason.append("operation", "create");
            //   jason.append("notification_key_name", user.getUserID());
            //  jason.append("registration_ids", user.getUserID());
            //  StringEntity postingString = new StringEntity(jason.toString());
            // httpPost.setEntity(postingString);
            HttpResponse response = client.execute(httpPost);

            System.out.println(response.toString());
            HttpEntity entity = response.getEntity();
            String resp = EntityUtils.toString(entity);
            // JSONObject result = new JSONObject(resp);
            //TODO: Errorhandling
            //authID = (String) result.get("notification_key");
        } catch (Exception e) {
            //TODO:Errorhandling
            System.out.println("hier: " + e.toString());
        }
    }
}
