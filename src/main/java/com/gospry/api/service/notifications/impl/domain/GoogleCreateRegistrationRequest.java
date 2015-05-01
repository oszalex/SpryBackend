package com.gospry.api.service.notifications.impl.domain;

import org.json.JSONException;
import org.json.JSONObject;
import org.thymeleaf.util.StringUtils;

import java.util.Collections;
import java.util.UUID;

/**
 * Created by chris on 25/04/15.
 */
public class GoogleCreateRegistrationRequest extends GoogleRegistrationRequest {
    private final String notificationKeyName;

    public GoogleCreateRegistrationRequest(String authid, long userId){
        this.notificationKeyName = String.format("%d%.4s", userId, UUID.randomUUID().toString());
        this.operation= GoogleRegistrationOperation.CREATE;
        this.registrationIds = Collections.singletonList(authid);
    }


    @Override
    public String toJson() {
        JSONObject jason = new JSONObject();
        try {
            jason.append("registration_ids", StringUtils.join(registrationIds, ","));
            jason.put("operation", "create");
            jason.put("notification_key_name", notificationKeyName);
        } catch (JSONException e) {
            //TODO: Error
        }

        /*
        StringBuilder sb = new StringBuilder();

        // start json
        sb.append("{");

        // add operation
        sb.append("operation:" + operation.toString() + ",");

        // add notification key
        sb.append("notification_key_name:" + notificationKeyName + ",");

        // add registration identifier
        sb.append("registration_ids:" + StringUtils.join(registrationIds, ",") + "");

        // end json
        sb.append("}");
*/
        return jason.toString();
    }
}
