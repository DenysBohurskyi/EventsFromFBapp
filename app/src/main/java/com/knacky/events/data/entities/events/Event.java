
package com.knacky.events.data.entities.events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Event {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("coverPicture")
    @Expose
    private String coverPicture;
    @SerializedName("profilePicture")
    @Expose
    private String profilePicture;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("distance")
    @Expose
    private int distance;
    @SerializedName("startTime")
    @Expose
    private String startTime;
    @SerializedName("endTime")
    @Expose
    private String endTime;
    @SerializedName("timeFromNow")
    @Expose
    private int timeFromNow;
    @SerializedName("isCancelled")
    @Expose
    private boolean isCancelled;
    @SerializedName("isDraft")
    @Expose
    private boolean isDraft;
    @SerializedName("category")
    @Expose
    private Object category;
    @SerializedName("place")
    @Expose
    private Place place;
    @SerializedName("stats")
    @Expose
    private Stats stats;
    @SerializedName("distances")
    @Expose
    private Distances distances;
    @SerializedName("venue")
    @Expose
    private Venue venue;
//////////////////////////////////////////////

    @SerializedName("ticketing")
    @Expose
    private Ticketing ticketing;

    public Ticketing getTicketing() {

        return ticketing;
    }

    public void setTicketing(Ticketing ticketing) {
        this.ticketing = ticketing;
    }
//////////////////////////////////////////////
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
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

    public int getTimeFromNow() {
        return timeFromNow;
    }

    public void setTimeFromNow(int timeFromNow) {
        this.timeFromNow = timeFromNow;
    }

    public boolean isIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    public boolean isIsDraft() {
        return isDraft;
    }

    public void setIsDraft(boolean isDraft) {
        this.isDraft = isDraft;
    }

    public Object getCategory() {
        return category;
    }

    public void setCategory(Object category) {
        this.category = category;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public Distances getDistances() {
        return distances;
    }

    public void setDistances(Distances distances) {
        this.distances = distances;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

}
