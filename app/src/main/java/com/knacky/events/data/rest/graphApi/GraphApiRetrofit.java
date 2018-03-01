package com.knacky.events.data.rest.graphApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by knacky on 07.02.2018.
 */

public class GraphApiRetrofit {

    public static GraphRequests graphRequests;
    private static Retrofit graphRetrofit;

    public static void createGraphRetrofit(){
        //create okHttpClient
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        //logging Http
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClientBuilder.addInterceptor(loggingInterceptor);

        graphRetrofit = new Retrofit.Builder()
                .baseUrl("https://graph.facebook.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClientBuilder.build())    //only for http request writing to log
                .build();
        graphRequests = graphRetrofit.create(GraphRequests.class);
    }

    public static GraphRequests getGraphRequests(){
        return graphRequests;
    }
}
