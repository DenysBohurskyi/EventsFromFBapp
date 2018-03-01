package com.knacky.events.domain.usecases;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.knacky.events.R;
import com.knacky.events.data.entities.events.EventsModel;
import com.knacky.events.data.repository.EventsRepository;
import com.knacky.events.data.repository.EventsRepositoryImpl;

import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by knacky on 20.02.2018.
 */

public class EventsUseCase {



    Context context;
    View view;



    public EventsUseCase(Context context, View view) {
        this.context = context;
        this.view = view;



    }


    public void uploadEvents() {


    }



}
