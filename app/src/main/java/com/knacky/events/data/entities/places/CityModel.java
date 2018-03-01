package com.knacky.events.data.entities.places;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CityModel {

    @SerializedName("data")
    @Expose
    private List<Places> placesIdList = null;

    public List<Places> getData() {
        return placesIdList;
    }

    public void setData(List<Places> data) {
        this.placesIdList = data;
    }

    @Override
    public String toString() {
        return "CityModel: " + super.toString();
    }
}
