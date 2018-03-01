
package com.knacky.events.data.entities.events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Metadata {

    @SerializedName("venues")
    @Expose
    private int venues;
    @SerializedName("venuesWithEvents")
    @Expose
    private int venuesWithEvents;
    @SerializedName("events")
    @Expose
    private int events;

    public int getVenues() {
        return venues;
    }

    public void setVenues(int venues) {
        this.venues = venues;
    }

    public int getVenuesWithEvents() {
        return venuesWithEvents;
    }

    public void setVenuesWithEvents(int venuesWithEvents) {
        this.venuesWithEvents = venuesWithEvents;
    }

    public int getEventsNumber() {
        return events;
    }

    public void setEvents(int events) {
        this.events = events;
    }

}
