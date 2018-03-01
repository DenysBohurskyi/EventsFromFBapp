
package com.knacky.events.data.entities.events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Distances {

    @SerializedName("venue")
    @Expose
    private int venue;
    @SerializedName("event")
    @Expose
    private int event;

    public int getVenue() {
        return venue;
    }

    public void setVenue(int venue) {
        this.venue = venue;
    }

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

}
