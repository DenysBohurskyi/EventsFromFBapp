package com.knacky.events.presentation.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jackandphantom.circularprogressbar.CircleProgressbar;
import com.knacky.events.R;
import com.knacky.events.data.entities.events.EventsModel;
import com.knacky.events.presentation.MainActivity;
import com.knacky.events.presentation.adapters.EventListAdapter;
import com.knacky.events.presentation.presenters.EventsListPresenter;
import com.knacky.events.presentation.presenters.EventsListPresenterImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by knacky on 07.02.2018.
 */

public class EventsListFragment extends Fragment implements EventsListPresenter.EventsListView {
    EventsListPresenter eventsListPresenter;

    @BindView(R.id.circleProgressBar)
    CircleProgressbar circleProgressbar;

    @BindView(R.id.eventsListRecycleView)
    RecyclerView recyclerView;

    @BindView(R.id.create_event_btn_imgview)
    ImageView createEventBtn;

    EventListFragmentListener onEventListFragmentListener;

    private RecyclerView.Adapter recViewAdapter;

    //onEventListItemClickListener

    public interface EventListFragmentListener { //clickListener
        void onEventItemClick(String id);
        void onCreateEventBtnClicked();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_list_fragment_layout, null, false);
        ButterKnife.bind(this, view);
//        try {///////////////////////////////////////////
//            logMethod();
//        } catch (ParseException e) {
//           Log.v("CurTime", "exception: ");
//           e.printStackTrace();
//        }

        onEventListFragmentListener = (MainActivity) getActivity();

        eventsListPresenter = new EventsListPresenterImpl(getContext());
        eventsListPresenter.setEventListView(this);


        if (savedInstanceState == null) {
            Log.v("ListFragment", "savedInstanceState is null");
            createCircleProgressBar();
            eventsListPresenter.uploadEvents();
        } else {
            Log.v("ListFragment", "savedInstanceState is not null: " + savedInstanceState.toString());
        }

        return view;
    }

    private void createCircleProgressBar() {
        circleProgressbar.setVisibility(View.VISIBLE);
        circleProgressbar.setClockwise(true); //->
        int animationDuration = 2000; // 2500ms = 2,5s
        circleProgressbar.setProgressWithAnimation(0, animationDuration); // Default duration = 1500ms
    }

    @Override
    public void onEventsGet(EventsModel eventsModel) {

        createEventsRecycleView(eventsModel);
        circleProgressbar.setVisibility(View.GONE);

    }

    public void createEventsRecycleView(EventsModel eventsModel) {

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recViewAdapter = new EventListAdapter(getContext(), eventsModel, onEventListFragmentListener);
        recyclerView.setAdapter(recViewAdapter);

    }

    @OnClick(R.id.create_event_btn_imgview)
    public void createEventClick(){
        onEventListFragmentListener.onCreateEventBtnClicked();

//            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                    Uri.parse("http://maps.google.com/maps?daddr=" + "краснопольская+1в"));
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
//
//            getContext().startActivity(intent);

    }

    private void logMethod() throws ParseException {
//time after midnight
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        long howMany = 86400000L - (c.getTimeInMillis() - System.currentTimeMillis());
        Log.v("CurTime", "Current time: " + System.currentTimeMillis());
        Log.v("CurTime", "timeBeforeMidnight: " + howMany);
    }

}
