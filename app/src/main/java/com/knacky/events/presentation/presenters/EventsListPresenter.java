package com.knacky.events.presentation.presenters;

import com.knacky.events.data.entities.events.EventsModel;

/**
 * Created by knacky on 20.02.2018.
 */

public interface EventsListPresenter<T> {

    interface EventsListView {
        void onEventsGet(EventsModel eventsModel);
    }

    void setEventListView(T view);

    void uploadEvents();
}
