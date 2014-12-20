package com.gospry.api.presentation;

import com.gospry.api.domain.Happening;
import com.gospry.api.domain.User;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Alex on 19.12.2014.
 */
public class Ac2dmPushNotificationServiceImpl {
    private final static String authKey = "AIzaSyCdD13P5wYhLzXmXfg_OvRIFci36MgNluM";
    private final static String project_id = "343359709951";
    private String sendingRoleAccount;
    private String sendingRolePassword;

    public static String createNotificationUser(String authid, User user) {
        String authID = "";
        String resp = "";
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost("http://android.googleapis.com/gcm/notification");
            httpPost.addHeader("Authorization", "key=" + authKey);
            httpPost.addHeader("project_id", project_id);
            //   httpPost.addHeader("content-type", "application/json");

            JSONObject jason = new JSONObject();
            if (user.getGoogleauthenticationkey().equals("")) {
                jason.put("operation", "create");
            } else {
                jason.put("operation", "add");
                jason.put("notification_key", user.getGoogleauthenticationkey());
            }
            jason.put("notification_key_name", Long.toString(user.getUserID()));
            jason.append("registration_ids", authid);
            String test = jason.toString();
            StringEntity myEntity = new StringEntity(jason.toString(),
                    ContentType.create("application/json", "UTF-8"));
            httpPost.setEntity(myEntity);
            HttpResponse response = client.execute(httpPost);
            System.out.println(response.toString());
            System.out.println(response.getEntity().toString());
            HttpEntity entity = response.getEntity();
            resp = EntityUtils.toString(entity);
            JSONObject result = new JSONObject(resp);
            //TODO: Errorhandling
            authID = (String) result.get("notification_key");
        } catch (Exception e) {
            //TODO:Errorhandling
            System.out.println("Error while getting Notification Key: " + e.toString() + " Response " + resp);
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
    public static void sendInviteNotification(Happening happening, User user) {
        try {
            JSONObject jason = new JSONObject();
            jason.put("eventID", Long.toString(happening.getID()));
            sendNotification(user, jason);
        } catch (Exception e) {
            System.out.println("Error Creating Notification: " + e.toString());
        }

    }

    private static void sendNotification(User user, JSONObject payload) throws HttpException, IOException {
        String authID = "";
        String resp = "";
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost("https://android.googleapis.com/gcm/send");
            httpPost.addHeader("Authorization", "key=" + authKey);

            JSONObject jason = new JSONObject();
            jason.put("notification_key", user.getGoogleauthenticationkey());
            for (String id : user.getGoogleauthenticationids()) {
                jason.append("registration_ids", id);
            }
            jason.put("data", payload);
            StringEntity myEntity = new StringEntity(jason.toString(),
                    ContentType.create("application/json", "UTF-8"));

            httpPost.setEntity(myEntity);
            HttpResponse response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            resp = EntityUtils.toString(response.getEntity());
            System.out.println("Response: " + resp + "  " + response.toString());
            System.out.println();
            //TODO: Errorhandling
        } catch (Exception e) {
            //TODO: Errorhandling
            System.out.println("Error sending Notification: " + e.toString() + " Response " + resp);
        }
    }
}
