package com.knacky.events.data.entities.realm;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by knacky on 25.02.2018.
 */

public class RealmEvents extends RealmObject {
    //ticket !!!!!!!!
    private String
            idEvent,
            nameEvent,
            type,
            coverPictureEvent,
            profilePictureEvent,
            description,
            distance,
            startTime,
            endTime,
            city,
            country,
            latitude,
            longitude,
            street,
            attending,
            maybe,
            idPlace,
            namePlace,
            aboutPlace,
            ticketUri,
            coverPicturePlace,
            profilePicturePlace;

    private boolean isCancelled;
    private RealmList<String> emails = null;
    private RealmList<String> categoryPlace = null;

    public String getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(String idEvent) {
        this.idEvent = idEvent;
    }

    public String getNameEvent() {
        return nameEvent;
    }

    public void setNameEvent(String nameEvent) {
        this.nameEvent = nameEvent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCoverPictureEvent() {
        return coverPictureEvent;
    }

    public void setCoverPictureEvent(String coverPictureEvent) {
        this.coverPictureEvent = coverPictureEvent;
    }

    public String getProfilePictureEvent() {
        return profilePictureEvent;
    }

    public void setProfilePictureEvent(String profilePictureEvent) {
        this.profilePictureEvent = profilePictureEvent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean getIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAttending() {
        return attending;
    }

    public void setAttending(String attending) {
        this.attending = attending;
    }

    public String getMaybe() {
        return maybe;
    }

    public void setMaybe(String maybe) {
        this.maybe = maybe;
    }

    public String getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(String idPlace) {
        this.idPlace = idPlace;
    }

    public String getNamePlace() {
        return namePlace;
    }

    public void setNamePlace(String namePlace) {
        this.namePlace = namePlace;
    }

    public String getAboutPlace() {
        return aboutPlace;
    }

    public void setAboutPlace(String aboutPlace) {
        this.aboutPlace = aboutPlace;
    }

    public String getTicketUri() {
        return ticketUri;
    }

    public void setTicketUri(String ticketUri) {
        this.ticketUri = ticketUri;
    }

    public String getCoverPicturePlace() {
        return coverPicturePlace;
    }

    public void setCoverPicturePlace(String coverPicturePlace) {
        this.coverPicturePlace = coverPicturePlace;
    }

    public String getProfilePicturePlace() {
        return profilePicturePlace;
    }

    public void setProfilePicturePlace(String profilePicturePlace) {
        this.profilePicturePlace = profilePicturePlace;
    }

    public RealmList<String> getEmails() {
        return emails;
    }

    public void setEmails(RealmList<String> emails) {
        this.emails = emails;
    }

    public RealmList<String> getCategoryPlace() {
        return categoryPlace;
    }

    public void setCategoryPlace(RealmList<String> categoryPlace) {
        this.categoryPlace = categoryPlace;
    }



}
