package com.knacky.events.data.rest.tobilgEventsApi;


import com.knacky.events.data.entities.events.EventsModel;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by knacky on 20.02.2018.
 */

public interface TobilgApi {

    @GET("/events?")
    Observable<EventsModel> getEventsFromServer (@Query("lat") String latitude,
                                                 @Query("lng") String longitude,
                                                 @Query("distance") String distance);
}
