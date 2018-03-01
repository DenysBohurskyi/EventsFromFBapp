package com.knacky.events;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by knacky on 07.02.2018.
 */

public class EventsApplication extends Application {
    public static EventsApplication eventsApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        eventsApplication = this;
        Realm.init(this);
        configureRealm();
    }


    private void configureRealm() {
        //Realm.init(this);

        RealmConfiguration realmConfig = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(realmConfig);
    }
}
