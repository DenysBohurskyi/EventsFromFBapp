
package com.knacky.events.data.entities.events;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Venue {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("emails")
    @Expose
    private List<String> emails = null;
    @SerializedName("coverPicture")
    @Expose
    private String coverPicture;
    @SerializedName("profilePicture")
    @Expose
    private String profilePicture;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("categoryList")
    @Expose
    private List<String> categoryList = null;
    @SerializedName("location")
    @Expose
    private VenueLocation venueLocation;

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

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<String> categoryList) {
        this.categoryList = categoryList;
    }

    public VenueLocation getVenueLocation() {
        return venueLocation;
    }

    public void setVenueLocation(VenueLocation venueLocation) {
        this.venueLocation = venueLocation;
    }

}
