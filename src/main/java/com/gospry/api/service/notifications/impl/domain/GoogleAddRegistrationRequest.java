package com.gospry.api.service.notifications.impl.domain;

import org.thymeleaf.util.StringUtils;
import java.util.Collections;

/**
 * Created by chris on 25/04/15.
 */
public class GoogleAddRegistrationRequest extends GoogleRegistrationRequest {
    private final String notificationKey;

    public GoogleAddRegistrationRequest(String authid, String notificationKey){
        this.notificationKey = notificationKey;
        this.operation = GoogleRegistrationOperation.ADD;
        this.registrationIds = Collections.singletonList(authid);
    }

    @Override
    public String toJson() {
        StringBuilder sb = new StringBuilder();

        // start json
        sb.append("{");

        // add operation
        sb.append("operation:" + operation.toString() + ",");

        // add notification key
        sb.append("notification_key:" + notificationKey + ",");

        // add registration identifier
        sb.append("registration_ids:[" + StringUtils.join(registrationIds, ",") + "]");

        // end json
        sb.append("}");

        return sb.toString();
    }
}
