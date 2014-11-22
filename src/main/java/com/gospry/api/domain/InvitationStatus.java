package com.gospry.api.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum InvitationStatus {
    INVITED("invited"), NOT_INVITED("not_invited"), ATTENDING("attending"), MAYBE("maybe"), NOT_ATTENDING("not_attending");

    private final String value;

    private InvitationStatus(String value) { this.value = value; }

    @JsonValue
    public String value() { return value; }


    @JsonCreator
    public static InvitationStatus forValue(String v) {
        if(v.equals("invited"))
            return InvitationStatus.INVITED;
        if(v.equals("not_attending"))
            return InvitationStatus.NOT_ATTENDING;
        if(v.equals("attending"))
            return InvitationStatus.ATTENDING;
        if(v.equals("maybe"))
            return InvitationStatus.MAYBE;

        return InvitationStatus.NOT_INVITED;
    }
}
