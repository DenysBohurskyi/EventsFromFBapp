package com.knacky.events.data.entities.realm;

import io.realm.RealmObject;

/**
 * Created by knacky on 29.05.2018.
 */

public class RealmFirebaseEventObject extends RealmObject{
    String crEventImgUri, crEventName, crEventDate, crEventCatecory, crEventDescription, ceEventVenue, author;


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
