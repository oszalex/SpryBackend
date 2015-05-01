package com.gospry.api.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum InvitationStatus {
    INVITED("invited"), NOT_INVITED("not_invited"), ATTENDING("attending"), MAYBE("maybe"), NOT_ATTENDING("not_attending");

    private final String value;

    private InvitationStatus(String value) {
        this.value = value;
    }

    @JsonCreator
    public static InvitationStatus forValue(String v) {
        System.out.println("Setting Value for Invitationstatus "+v);
        if (v.equals("INVITED"))
            return InvitationStatus.INVITED;
        if (v.equals("NOT_ATTENDING"))
            return InvitationStatus.NOT_ATTENDING;
        if (v.equals("ATTENDING"))
            return InvitationStatus.ATTENDING;
        if (v.equals("MAYBE"))
            return InvitationStatus.MAYBE;

        return InvitationStatus.NOT_INVITED;
    }

    @JsonValue
    public String value() {
        return value;
    }
}
