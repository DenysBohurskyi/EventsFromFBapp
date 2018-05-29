package com.knacky.events.data.entities.realm;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by knacky on 29.05.2018.
 */
public class RealmFirebaseEventsList extends RealmObject {

    private RealmList<RealmFirebaseEventObject> events;

    public RealmList<RealmFirebaseEventObject> getEvents() {
        return events;
    }

    public void setEvents(RealmList<RealmFirebaseEventObject> events) {
        this.events = events;
    }
}
