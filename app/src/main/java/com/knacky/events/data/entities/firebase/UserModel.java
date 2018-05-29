package com.knacky.events.data.entities.firebase;

/**
 * Created by knacky on 24.05.2018.
 */
public class UserModel {
    String email, fullName, profileImgUrl, password;

    public UserModel(){}

    public UserModel(String fullName, String profileImgUrl, String email, String password) {
        this.email = email;
        this.fullName = fullName;
        this.profileImgUrl = profileImgUrl;
        this.password = password;
        }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfileImgUrl() {
        return profileImgUrl;
    }

    public void setProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String provider) {
        this.password = password;
    }
}