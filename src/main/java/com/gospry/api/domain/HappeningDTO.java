package com.gospry.api.domain;

/**
 * Created by lexy on 05.05.15.
 */
public class HappeningDTO {
    Happening happening;
    InvitationStatus status;

    public HappeningDTO(InvitationStatus status, Happening happening) {
        this.status = status;
        this.happening = happening;
    }

    public Happening getHappening() {
        return happening;
    }

    public void setHappening(Happening happening) {
        this.happening = happening;
    }

    public InvitationStatus getStatus() {
        return status;
    }

    public void setStatus(InvitationStatus status) {
        this.status = status;
    }
}
