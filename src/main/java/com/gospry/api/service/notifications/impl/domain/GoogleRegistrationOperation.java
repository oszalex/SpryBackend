package com.gospry.api.service.notifications.impl.domain;

/**
 * Created by chris on 26/04/15.
 */
public enum GoogleRegistrationOperation {
    CREATE("create"), ADD("add");

    private final String value;

    private GoogleRegistrationOperation(String value){
        this.value=value;
    }

    @Override
    public String toString() {
        return value;
    }
}
