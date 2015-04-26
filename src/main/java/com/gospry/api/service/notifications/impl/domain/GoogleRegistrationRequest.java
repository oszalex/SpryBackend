package com.gospry.api.service.notifications.impl.domain;

import com.gospry.api.service.notifications.IGoogleRequest;
import com.gospry.api.service.notifications.impl.domain.GoogleRegistrationOperation;

import java.util.List;

/**
 * Created by chris on 25/04/15.
 */
public abstract class GoogleRegistrationRequest implements IGoogleRequest {
    protected GoogleRegistrationOperation operation;
    protected List<String> registrationIds;
}
