package com.gospry.api.service.notifications.impl.domain;

import com.gospry.api.service.notifications.IGoogleRequest;
import org.thymeleaf.util.StringUtils;

import java.util.List;

/**
 * Created by chris on 25/04/15.
 */
public class GoogleNotificationRequest implements IGoogleRequest {
    private String notificationKey;
    private List<String> registrationIds;
    private long eventId;

    public GoogleNotificationRequest(String notificationKey, List<String> registrationIds, long eventId) {
        this.notificationKey = notificationKey;
        this.registrationIds = registrationIds;
        this.eventId = eventId;
    }


    @Override
    public String toJson(){
        StringBuilder sb = new StringBuilder();

        // start json
        sb.append("{");

        // notification key
        sb.append("notification_key:" + notificationKey + ",");

        // notification Ids
        sb.append("registration_ids:[" + StringUtils.join(registrationIds, ",") + "],");

        // event id
        sb.append("data: {");
        sb.append("eventID:" + eventId);
        sb.append("}");

        // end json
        sb.append("}");

        return sb.toString();
    }

}
