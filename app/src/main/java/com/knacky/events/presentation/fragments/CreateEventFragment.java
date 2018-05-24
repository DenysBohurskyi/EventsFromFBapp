package com.knacky.events.presentation.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.knacky.events.R;

import butterknife.ButterKnife;

/**
 * Created by knacky on 24.05.2018.
 */
public class CreateEventFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_event_fragment, null, false);

        ButterKnife.bind(this, view);



//        initButtons();
        return view;
    }

}
