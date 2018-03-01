package com.knacky.events.data.rest.tobilgEventsApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by knacky on 20.02.2018.
 */

public class TobilgEventsRetrofit {

    public static TobilgApi tobilgApi;
    private static Retrofit toBilgRetrofit;

    public static void createEventRetrofit() {
        //create okHttpClient
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        //logging Http
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClientBuilder.addInterceptor(loggingInterceptor);

        if (toBilgRetrofit == null) {

            toBilgRetrofit = new Retrofit.Builder()
                    .baseUrl("https://facebook-events-by-location.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClientBuilder.build())    //only for http request writing to log
                    .build();
            if (tobilgApi == null) {
                tobilgApi = toBilgRetrofit.create(TobilgApi.class);
            }
        }

    }

    public static TobilgApi getTobilgApi() {
        return tobilgApi;
    }
}

