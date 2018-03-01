package com.knacky.events.presentation.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.knacky.events.R;
import com.knacky.events.data.entities.realm.RealmEvents;
import com.knacky.events.data.entities.realm.RealmEventsObject;
import com.knacky.events.presentation.presenters.EventPagePresenter;
import com.knacky.events.presentation.presenters.EventPagePresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

/**
 * Created by knacky on 21.02.2018.
 */

public class EventPageFragment extends Fragment implements EventPagePresenter.EventsPageView{

    public static EventPageFragment newInstance(String id) {
        EventPageFragment eventPageFragment = new EventPageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("eventId", id);
        eventPageFragment.setArguments(bundle);
        return eventPageFragment;
    }
    private String eventId;

    EventPagePresenter eventPagePresenter;

    @BindView(R.id.evPageMainPict)
    ImageView eventPictureImgView;
    @BindView(R.id.evNamePageTV)
    TextView eventNameTV;
    @BindView(R.id.evTicketTV)
    TextView eventTicketUriTV;
    @BindView(R.id.evPageAdressTV)
    TextView eventAdressTV;
    @BindView(R.id.evDatePageTV)
    TextView eventDateTV;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_page_fragment_layout, null, false);

        ButterKnife.bind(this, view);

        eventPagePresenter = new EventPagePresenterImpl(getContext());
        eventPagePresenter.setEventPageView(this);

        eventId = getArguments().getString("eventId");
        eventPagePresenter.getDataFromRealm(eventId);
        return view;
    }

    @Override
    public void onGetRealmData(RealmResults<RealmEvents> realmResults) {
        fillPageFields(realmResults);
    }

    private void fillPageFields(RealmResults<RealmEvents> realmResults) {
        //set event picture
        Glide
                .with(getContext())
                .load(realmResults.get(0).getCoverPictureEvent())
                .into(eventPictureImgView);

        eventNameTV.setText(realmResults.get(0).getNameEvent());
        eventDateTV.setText(realmResults.get(0).getStartTime());
        eventAdressTV.setText(realmResults.get(0).getCity() + ", " + realmResults.get(0).getStreet());
        eventTicketUriTV.setText(realmResults.get(0).getTicketUri());
    }
}
