package com.knacky.events.data.repository;

import com.knacky.events.data.entities.events.EventsModel;

import rx.Observable;


/**
 * Created by knacky on 20.02.2018.
 */

public interface EventsRepository {
    void createGraphConnection();
    Observable<EventsModel> getEvents();
    EventsModel eventsMapper(EventsModel eventsModel);
//    void getEventsNumber();

}
