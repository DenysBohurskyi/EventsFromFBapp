package com.knacky.events.presentation;


import android.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.knacky.events.R;
import com.knacky.events.presentation.fragments.EventsListFragment;
import com.knacky.events.presentation.fragments.SignInDialogFragment;

public class MainActivity extends AppCompatActivity implements EventsListFragment.EventListFragmentListener {
    EventsListFragment eventsListFragment;
    DialogFragment signInDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eventsListFragment = new EventsListFragment();
        signInDialogFragment = new SignInDialogFragment();

        replaceFragment(eventsListFragment);

//        ConstraintLayout constraintEventItemLayout = findViewById(R.id.constraint_event_item_layout);

        // ButterKnife.bind(this);
    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.ev_container, fragment)
                .commit();
    }

    @Override
    public void onItemClick(String id) {

        Log.i("onClick", "ev id: " + id);
        signInDialogFragment.show(getFragmentManager(), "signInDialogFragment");
        //replaceFragment(EventPageFragment.newInstance(id));

    }



//
//    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
//        // Find the TextView that is inside of the SignInButton and set its text
//        for (int i = 0; i < signInButton.getChildCount(); i++) {
//            View v = signInButton.getChildAt(i);
//
//            if (v instanceof TextView) {
//                TextView tv = (TextView) v;
//                tv.setText(buttonText);
//                return;
//            }
//        }
//    }
}
