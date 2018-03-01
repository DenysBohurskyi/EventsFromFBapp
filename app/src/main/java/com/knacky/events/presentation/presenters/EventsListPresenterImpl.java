package com.knacky.events.presentation.presenters;

import android.content.Context;
import android.util.Log;

import com.knacky.events.data.entities.events.EventsModel;
import com.knacky.events.data.entities.realm.RealmEvents;
import com.knacky.events.data.entities.realm.RealmEventsObject;
import com.knacky.events.data.repository.EventsRepository;
import com.knacky.events.data.repository.EventsRepositoryImpl;
import com.knacky.events.presentation.fragments.EventsListFragment;

import io.realm.Realm;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by knacky on 20.02.2018.
 */

public class EventsListPresenterImpl<T extends EventsListPresenter.EventsListView> implements EventsListPresenter {
    EventsRepository eventsRepository;
    Subscription subscription;
    //EventsUseCase eventsUseCase;
    EventsListFragment eventsListFragment;


    Context context;
    T view;

    public EventsListPresenterImpl(Context context) {
        this.context = context;
        eventsRepository = new EventsRepositoryImpl(context);

    }


    @Override
    public void setEventListView(Object view) {
        this.view = (T) view;
    }

    @Override
    public void uploadEvents() {
        //   eventsRepository.getEventsNumber();
        if (subscription != null) subscription.unsubscribe();

        subscription = eventsRepository.getEvents()
                .subscribeOn(Schedulers.io())
                .subscribe(it -> {
                            Log.v("Request", "toBilg Succeed: " + it.toString());

                            view.onEventsGet(eventsRepository.eventsMapper(it));
                            fillEventsDB(eventsRepository.eventsMapper(it));
//                            eventsListFragment.createEventsModel(it);
                        },
                        throwable -> {
                            Log.v("Request", "toBilg Failed: " + throwable.toString());
                        }
                );
        //   subscription.unsubscribe();
    }

    public void fillEventsDB(EventsModel evModel) {
        RealmEventsObject realmEventsObject;
        RealmEvents realmEvents;

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
//mainObj
        realmEventsObject = realm.createObject(RealmEventsObject.class);

        realmEventsObject.setEventsNumber(evModel.getMetadata().getEventsNumber());
        realmEventsObject.setVenuesNumber(evModel.getMetadata().getVenues());
        realmEventsObject.setVenuesWithEvents(evModel.getMetadata().getVenuesWithEvents());
        //delete WeatherObj from RealmDb
        //realm.delete(RealmEventsObject.class);
        Log.v("realm", "Number of events: " + evModel.getMetadata().getEventsNumber());

        for (int i = 0; i < evModel.getMetadata().getEventsNumber() - 1; i++) {
//subObj
            realmEvents = realm.createObject(RealmEvents.class);

            realmEvents.setIdEvent(evModel.getEvents().get(i).getId());
            realmEvents.setNameEvent(evModel.getEvents().get(i).getName());
            realmEvents.setType(evModel.getEvents().get(i).getType());
            realmEvents.setCoverPictureEvent(evModel.getEvents().get(i).getCoverPicture());
            realmEvents.setProfilePictureEvent(evModel.getEvents().get(i).getProfilePicture());
            realmEvents.setDescription(evModel.getEvents().get(i).getDescription());
            realmEvents.setDistance(Integer.toString(evModel.getEvents().get(i).getDistance()));
            realmEvents.setStartTime(evModel.getEvents().get(i).getStartTime());
            realmEvents.setStartTime(evModel.getEvents().get(i).getStartTime());
            realmEvents.setEndTime(evModel.getEvents().get(i).getEndTime());
            realmEvents.setEndTime(evModel.getEvents().get(i).getEndTime());
            realmEvents.setIsCancelled(evModel.getEvents().get(0).isIsCancelled());
            realmEvents.setCity(evModel.getEvents().get(i).getPlace().getPlaceLocation().getCity());
            realmEvents.setCountry(evModel.getEvents().get(i).getPlace().getPlaceLocation().getCountry());
            realmEvents.setLatitude(Float.toString(evModel.getEvents().get(i).getPlace().getPlaceLocation().getLatitude()));
            realmEvents.setLongitude(Float.toString(evModel.getEvents().get(i).getPlace().getPlaceLocation().getLongitude()));
            realmEvents.setStreet(evModel.getEvents().get(i).getPlace().getPlaceLocation().getStreet());
            realmEvents.setAttending(Integer.toString(evModel.getEvents().get(i).getStats().getAttending()));
            realmEvents.setMaybe(Integer.toString(evModel.getEvents().get(i).getStats().getMaybe()));
            realmEvents.setIdPlace(evModel.getEvents().get(i).getPlace().getId());
            realmEvents.setNamePlace(evModel.getEvents().get(i).getPlace().getName());
            realmEvents.setNamePlace(evModel.getEvents().get(i).getPlace().getName());
            realmEvents.setAboutPlace(evModel.getEvents().get(i).getVenue().getAbout());
            //ticketing
            if (evModel.getEvents().get(i).getTicketing() == null)
                realmEvents.setTicketUri("not required");
            else realmEvents.setTicketUri(evModel.getEvents().get(i).getTicketing().getTicketUri());
            realmEvents.setCoverPicturePlace(evModel.getEvents().get(i).getVenue().getCoverPicture());
            realmEvents.setProfilePicturePlace(evModel.getEvents().get(i).getVenue().getProfilePicture());
            //emails list
            realmEvents.getEmails().addAll(evModel.getEvents().get(i).getVenue().getEmails());
            //categories list
            realmEvents.getCategoryPlace().addAll(evModel.getEvents().get(i).getVenue().getCategoryList());

            realmEventsObject.getEvents().add(realmEvents);
        }

        realm.commitTransaction();
    }
}
