package com.knacky.events.data.repository;

import android.content.Context;
import android.util.Log;

import com.knacky.events.data.entities.events.Event;
import com.knacky.events.data.entities.events.EventsModel;
import com.knacky.events.data.rest.graphApi.GraphApiRetrofit;
import com.knacky.events.data.rest.tobilgEventsApi.TobilgEventsRetrofit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by knacky on 20.02.2018.
 */

public class EventsRepositoryImpl implements EventsRepository {

    Context context;
    int position = 0;


    EventsModel eventsModel;
    private Subscription graphSubscription;
    private Subscription toBilgEventsSubscription;
    private String ACCESS_TOKEN = "150285165681187|mUWpwfMERqTfMybHZiRcQ8LFCIg";
    private String EVENT_ID = "1172235782812088";

    public EventsRepositoryImpl(Context context) {
        this.context = context;


//        recyclerView = view.findViewById(R.id.eventsListRecycleView);
//
//        ButterKnife.bind(this, view);
    }


    public Observable<EventsModel> getEvents() {
        TobilgEventsRetrofit.createEventRetrofit();

        return TobilgEventsRetrofit.getTobilgApi()
                .getEventsFromServer("50.4546600", "30.52380001", "100")
                .observeOn(AndroidSchedulers.mainThread());
//                .map(it -> eventsMapper(it));
//                .map(it -> eventsModel = it);
    }

    public EventsModel eventsMapper(EventsModel eventsModel) {
//        List<String> eventsNamesList = new ArrayList<>();
//        eventsNamesList.add("exampleName");
//        Log.v("NameFilter", "List size: " + eventsNamesList.size());
        List<Event> newEvents = eventsModel.getEvents();

        Log.v("NameFilter", "ListSize: " + eventsModel.getEvents().size());
        for (int i = 0; i < eventsModel.getEvents().size(); i++) {
            for (int j = i + 1; j < newEvents.size(); ) {
                if (eventsModel.getEvents().get(i).getName().toString().equals(newEvents.get(j).getName().toString())) {
//                eventsModel.getEvents().remove(j);
                    Log.v("NameFilter", "equals,event name" + "\n" + i + ": " + eventsModel.getEvents().get(i).getName()
                            + "\n" + j + ": " + eventsModel.getEvents().get(j).getName());
                    newEvents.remove(j);
                    eventsModel.getEvents().remove(j-1);


                } else {
                    Log.v("NameFilter", "NOT EQUALS1: " + "\n" + i + ": " + eventsModel.getEvents().get(i).getName() +
                            "\n" + j + ": " + eventsModel.getEvents().get(j).getName());
                    j++;
                }
            }
        }
//
//        for (String listName : eventsNamesList) {
//            for (Event event : eventsModel.getEvents()) {
//                if (listName == event.getName()) {
//                    Log.v("NameFilter", "listName = evName");
//                    eventsModel.getEvents().remove(event);
//                } else {
//                    eventsNamesList.add(event.getName());
//                    Log.v("NameFilter", "listName != evName, ListSize: " + eventsNamesList.size());
//                }
//            }
//        }
//        List<Event> newEvents = eventsModel.getEvents();


        return eventsModel;
    }
//    namesUniqFilter(){}

//    public void getEventsNumber(){
//        TobilgEventsRetrofit.createEventRetrofit();
//
//        graphSubscription  =
//                TobilgEventsRetrofit.getTobilgApi()
//                .getEventsFromServer("50.4546600","30.52380001", "100")
//                .subscribeOn(Schedulers.io())
//
//                .subscribe(it -> {
//                            Log.v("Request","toBilg Succeed: " + it.toString() + " " + it.getMetadata().getEventsNumber());
//
////                            createEventsRecycleView(it);
//        },
//        throwable -> {Log.v("Request","toBilg Failed: " + throwable.toString());});
//    }

    public void createGraphConnection() {
        GraphApiRetrofit.createGraphRetrofit();

        graphSubscription = GraphApiRetrofit.getGraphRequests()
                .getPlacesFromServer(/*EVENT_ID ,*/ACCESS_TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(it -> {
                            Log.v("Request", "Graph Succeed: " + it.toString());
                            Log.v("Request", "id(7): " + it.getData().get(6).getId().toString());
                        },
                        throwable -> Log.v("Request", "Graph Failed: " + throwable.toString()));

    }


}
