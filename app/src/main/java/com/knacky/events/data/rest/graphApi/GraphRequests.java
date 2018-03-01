package com.knacky.events.data.rest.graphApi;

import com.knacky.events.data.entities.places.CityModel;
import com.knacky.events.data.entities.places.Places;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by knacky on 07.02.2018.
 */

public interface GraphRequests {

    @GET("/search?type=place&center=50.4546600,30.52380001&distance=2000&fields=id")
    Observable<CityModel> getPlacesFromServer (/*@Query("event-id") String eventId,*/
                                            @Query("access_token") String accessToken);


    @GET("/141958922545600?fields=id,plural,property_config,singular,type,article")
    Observable<Object> getEventsFromServer (/*@Query("event-id") String eventId,*/
                                            @Query("access_token") String accessToken);


}
