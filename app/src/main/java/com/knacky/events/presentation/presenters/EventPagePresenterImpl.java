package com.knacky.events.presentation.presenters;

import android.content.Context;
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

/**
 * Created by knacky on 26.02.2018.
 */

public class EventPagePresenterImpl<T extends EventPagePresenter.EventsPageView> implements EventPagePresenter {


    Context context;
    private Realm realm;
    private RealmResults<RealmEvents> realmResults;

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
}
