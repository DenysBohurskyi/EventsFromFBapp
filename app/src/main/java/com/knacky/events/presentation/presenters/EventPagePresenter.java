package com.knacky.events.presentation.presenters;

import com.knacky.events.data.entities.realm.RealmEvents;
import com.knacky.events.data.entities.realm.RealmEventsObject;

import io.realm.RealmResults;

/**
 * Created by knacky on 26.02.2018.
 */

public interface EventPagePresenter<T> {
    public interface EventsPageView{
        void onGetRealmData(RealmResults<RealmEvents> realmResults);
    }
    void setEventPageView(T view);
    void getDataFromRealm(String eventId);
}
