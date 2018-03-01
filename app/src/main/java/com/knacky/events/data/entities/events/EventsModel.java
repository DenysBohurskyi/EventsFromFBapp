
package com.knacky.events.data.entities.events;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventsModel {

    @SerializedName("events")
    @Expose

    private List<Event> events = null;

    @SerializedName("metadata")
    @Expose

    private Metadata metadata;



    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

}
