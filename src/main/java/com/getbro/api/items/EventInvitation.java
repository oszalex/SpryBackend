package com.getbro.api.items;

import com.getbro.api.items.InvitationStatus;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class EventInvitation {
    private long eventId;
    private long userID;
    private long inviterID;
    private InvitationStatus status;

    public EventInvitation(){}

    public EventInvitation(long eventId, long userID,
                           long inviter, InvitationStatus status) {
        this.eventId = eventId;
        this.userID = userID;
        this.inviterID = inviter;
        this.status = status;
    }

    public long getEventId() {
        return eventId;
    }
    public long getUserId() {
        return userID;
    }
    public long getInviterID() {
        return inviterID;
    }
    public InvitationStatus getStatus() {
        return status;
    }
}