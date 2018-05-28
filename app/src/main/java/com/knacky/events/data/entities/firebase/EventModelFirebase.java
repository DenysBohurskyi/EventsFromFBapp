package com.knacky.events.data.entities.firebase;

/**
 * Created by knacky on 24.05.2018.
 */
public class EventModelFirebase {
    String crEventImgUri, crEventName, crEventDescription, ceEventVenue, author;

    public EventModelFirebase(String crEventImgUri, String crEventName, String crEventDescription, String ceEventVenue, String author) {
        this.crEventImgUri = crEventImgUri;
        this.crEventName = crEventName;
        this.crEventDescription = crEventDescription;
        this.ceEventVenue = ceEventVenue;
        this.author = author;
    }

    public String getCrEventImgUri() {
        return crEventImgUri;
    }

    public void setCrEventImgUri(String crEventImgUri) {
        this.crEventImgUri = crEventImgUri;
    }

    public String getCrEventName() {
        return crEventName;
    }

    public void setCrEventName(String crEventName) {
        this.crEventName = crEventName;
    }

    public String getCrEventDescription() {
        return crEventDescription;
    }

    public void setCrEventDescription(String crEventDescription) {
        this.crEventDescription = crEventDescription;
    }

    public String getCeEventVenue() {
        return ceEventVenue;
    }

    public void setCeEventVenue(String ceEventVenue) {
        this.ceEventVenue = ceEventVenue;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}