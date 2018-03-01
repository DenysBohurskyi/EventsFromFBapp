package com.knacky.events.data.entities.events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by knacky on 25.02.2018.
 */

public class Ticketing {
    @SerializedName("attending")
    @Expose
    private String ticketUri;


    public String getTicketUri() {
        return ticketUri;
    }

    public void setTicketUri(String ticketUri) {
        this.ticketUri = ticketUri;
    }

}
