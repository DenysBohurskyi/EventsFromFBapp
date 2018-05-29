package com.knacky.events.data.entities.firebase;

/**
 * Created by knacky on 24.05.2018.
 */
public class EventModelFirebase {
    String crEventImgUri, crEventName, crEventDate, crEventCatecory, crEventDescription, ceEventVenue, author;

    public EventModelFirebase(){

    }

    public EventModelFirebase(String crEventImgUri, String crEventName, String crEventDate, String crEventCatecory,
                              String crEventDescription, String ceEventVenue, String author) {
        this.crEventImgUri = crEventImgUri;
        this.crEventName = crEventName;
        this.crEventDate = crEventDate;
        this.crEventCatecory = crEventCatecory;
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

    public String getCrEventDate() {
        return crEventDate;
    }

    public void setCrEventDate(String crEventDate) {
        this.crEventDate = crEventDate;
    }

    public String getCrEventCatecory() {
        return crEventCatecory;
    }

    public void setCrEventCatecory(String crEventCatecory) {
        this.crEventCatecory = crEventCatecory;
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