package com.knacky.events.presentation.presenters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

import com.bumptech.glide.Glide;
import com.knacky.events.R;
import com.knacky.events.data.entities.realm.RealmEvents;
import com.knacky.events.data.entities.realm.RealmEventsObject;
import com.knacky.events.data.entities.realm.RealmFirebaseEventObject;

/**
 * Created by knacky on 26.02.2018.
 */

public class EventPagePresenterImpl<T extends EventPagePresenter.EventsPageView> implements EventPagePresenter {


    Context context;
    private Realm realm;
    private RealmResults<RealmEvents> realmResults;
    private RealmResults<RealmFirebaseEventObject> realmResultsFirebase;

    T view;

    public EventPagePresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void setEventPageView(Object view) {
        this.view = (T) view;
    }

     public void getDataFromRealm(String eventId){
        realm = Realm.getDefaultInstance();
        realmResults = realm.where(RealmEvents.class).equalTo("idEvent", eventId).findAll();
        view.onGetRealmData(realmResults);
     }

    public void getDataFromRealmFirebase(String eventId){
        realm = Realm.getDefaultInstance();
        realmResultsFirebase = realm.where(RealmFirebaseEventObject.class).equalTo("crEventImgUri", eventId).findAll();
        view.onGetRealmDataFirebase(realmResultsFirebase);
    }

    public void openGoogleMapFB() {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=" + realmResults.get(0).getLatitude()+ "," + realmResults.get(0).getLongitude()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

        context.startActivity(intent);
    }

    public void openGoogleMap() {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=" + realmResultsFirebase.get(0).getCeEventVenue()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

        context.startActivity(intent);
    }
}
