
package com.knacky.events.data.entities.events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stats {

    @SerializedName("attending")
    @Expose
    private int attending;
    @SerializedName("declined")
    @Expose
    private int declined;
    @SerializedName("maybe")
    @Expose
    private int maybe;
    @SerializedName("noreply")
    @Expose
    private int noreply;

    public int getAttending() {
        return attending;
    }

    public void setAttending(int attending) {
        this.attending = attending;
    }

    public int getDeclined() {
        return declined;
    }

    public void setDeclined(int declined) {
        this.declined = declined;
    }

    public int getMaybe() {
        return maybe;
    }

    public void setMaybe(int maybe) {
        this.maybe = maybe;
    }

    public int getNoreply() {
        return noreply;
    }

    public void setNoreply(int noreply) {
        this.noreply = noreply;
    }

}
