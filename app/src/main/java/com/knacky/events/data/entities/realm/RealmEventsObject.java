package com.knacky.events.data.entities.realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by knacky on 25.02.2018.
 */

public class RealmEventsObject extends RealmObject {

//    @PrimaryKey
//    private long id;
    private int venuesNumber;
    private int venuesWithEvents;
    private int eventsNumber;

    private RealmList<RealmEvents> events;


//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }

    public int getVenuesNumber() {
        return venuesNumber;
    }

    public void setVenuesNumber(int venuesNumber) {
        this.venuesNumber = venuesNumber;
    }

    public int getVenuesWithEvents() {
        return venuesWithEvents;
    }

    public void setVenuesWithEvents(int venuesWithEvents) {
        this.venuesWithEvents = venuesWithEvents;
    }

    public int getEventsNumber() {
        return eventsNumber;
    }

    public void setEventsNumber(int eventsNumber) {
        this.eventsNumber = eventsNumber;
    }

    public RealmList<RealmEvents> getEvents() {
        return events;
    }

    public void setEvents(RealmList<RealmEvents> events) {
        this.events = events;
    }


}
