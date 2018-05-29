package com.knacky.events.presentation.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jackandphantom.circularprogressbar.CircleProgressbar;
import com.knacky.events.R;
import com.knacky.events.data.entities.events.EventsModel;
import com.knacky.events.data.entities.firebase.EventModelFirebase;
import com.knacky.events.data.entities.realm.RealmFirebaseEventObject;
import com.knacky.events.data.entities.realm.RealmFirebaseEventsList;
import com.knacky.events.extensions.Categories;
import com.knacky.events.presentation.MainActivity;
import com.knacky.events.presentation.adapters.EventListAdapter;
import com.knacky.events.presentation.adapters.FirebaseEventListAdapter;
import com.knacky.events.presentation.presenters.EventsListPresenter;
import com.knacky.events.presentation.presenters.EventsListPresenterImpl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import rx.Completable;


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

    @BindView(R.id.event_categories_root)
    Spinner searchEventCategory;

    EventListFragmentListener onEventListFragmentListener;

    private FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    private RecyclerView.Adapter recViewAdapter;

    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private List<EventModelFirebase> resultEventsList;

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

        onEventListFragmentListener = (MainActivity) getActivity();
        eventsListPresenter = new EventsListPresenterImpl(getContext());
        eventsListPresenter.setEventListView(this);

        resultEventsList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Events");

        initFirebaceRecycleView();
        initCategoriesAdapter();
//        displayEvents();

//        if (savedInstanceState == null) {
//            Log.v("ListFragment", "savedInstanceState is null");
//            createCircleProgressBar();
//            eventsListPresenter.uploadEvents();
//        } else {
//            Log.v("ListFragment", "savedInstanceState is not null: " + savedInstanceState.toString());
//        }

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
    public void createEventClick() {
        onEventListFragmentListener.onCreateEventBtnClicked();

//            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                    Uri.parse("http://maps.google.com/maps?daddr=" + "краснопольская+1в"));
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
//
//            getContext().startActivity(intent);

    }

    public void initFirebaceRecycleView() {

        //=====================================================================================
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recViewAdapter = new FirebaseEventListAdapter(getContext(), resultEventsList, onEventListFragmentListener);
        recyclerView.setAdapter(recViewAdapter);

        //=====================================================================================

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                resultEventsList.add(dataSnapshot.getValue(EventModelFirebase.class));
                recViewAdapter.notifyDataSetChanged();
                fillEventsDB(dataSnapshot.getValue(EventModelFirebase.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public Completable fillEventsDB(EventModelFirebase eventModelFirebase) {

        RealmFirebaseEventObject realmEventsObject;
        RealmFirebaseEventsList realmFirebaseEventsList;

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
//mainObj
        realmEventsObject = realm.createObject(RealmFirebaseEventObject.class);

        //delete WeatherObj from RealmDb
        realm.delete(RealmFirebaseEventsList.class);
//        Log.v("realm", "Number of events: " + evModel.getMetadata().getEventsNumber());

        for (int i = 0; i < resultEventsList.size() - 1; i++) {
//subObj
            realmFirebaseEventsList = realm.createObject(RealmFirebaseEventsList.class);

            realmEventsObject.setCrEventImgUri(eventModelFirebase.getCrEventImgUri());
            realmEventsObject.setCrEventName(eventModelFirebase.getCrEventName());
            realmEventsObject.setCrEventCatecory(eventModelFirebase.getCrEventCatecory());
            realmEventsObject.setCrEventDate(eventModelFirebase.getCrEventDate());
            realmEventsObject.setCrEventDescription(eventModelFirebase.getCrEventDescription());
            realmEventsObject.setCeEventVenue(eventModelFirebase.getCeEventVenue());
            realmEventsObject.setAuthor(eventModelFirebase.getAuthor());

            realmFirebaseEventsList.getEvents().add(realmEventsObject);
        }

        realm.commitTransaction();
        return Completable.complete();
    }

    public void initCategoriesAdapter() {
        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, Categories.searchEventCategories);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        searchEventCategory.setAdapter(adapter);
        // заголовок
        searchEventCategory.setPrompt(getResources().getString(R.string.event_categories));
        // выделяем элемент
        searchEventCategory.setSelection(0);
        // устанавливаем обработчик нажатия
        searchEventCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
//                if (position != 0)
//                    eventModelFirebase.setCrEventCatecory(searchEventCategory.getSelectedItem().toString());
                // показываем позиция нажатого элемента
//                Toast.makeText(getContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }
//    public void displayEvents() {
////Suppose you want to retrieve "chats" in your Firebase DB:
//        Query query = FirebaseDatabase.getInstance().getReference().child("Events");
//
//        FirebaseListOptions<EventModelFirebase> options = new FirebaseListOptions.Builder<EventModelFirebase>()
//                .setQuery(query, EventModelFirebase.class)
//                .setLifecycleOwner(this)            // important  syka line!!!!!
//                .setLayout(R.layout.eventlist_item_layout)
//                .build();
//        Log.v("Chat", "Display Chat< fireBaseOptions done");
//
//        adapter = new FirebaseListAdapter<ChatMessage>(options) {
//            @Override
//            protected void populateView(View v, ChatMessage model, int position) {
//
//                Log.v("Chat", "Model: " + model.toString() + "\nuser: " + model.getMessageUser());
//                // Get references to the views of message.xml
//                TextView messageText = v.findViewById(R.id.message_text);
//                TextView messageUser = v.findViewById(R.id.message_user);
//                TextView messageTime = v.findViewById(R.id.message_time);
//
//                messageText.setText(model.getMessageText());
//                messageUser.setText(model.getMessageUser());
//                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMessageTime()));
//            }
//        };
//        listOfMessages.setAdapter(adapter);
//    }

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
