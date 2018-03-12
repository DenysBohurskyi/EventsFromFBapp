package com.knacky.events.presentation;


import android.app.ActionBar;
import android.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.knacky.events.R;
import com.knacky.events.presentation.fragments.ChatFragment;
import com.knacky.events.presentation.fragments.EventPageFragment;
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

//        getActionBar().setHomeButtonEnabled(true);
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
        replaceFragment(EventPageFragment.newInstance(id));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.account_item)
            signInDialogFragment.show(getFragmentManager(), "signInDialogFragment");
        else if (item.getItemId() == R.id.chat_item) {
            if (FirebaseAuth.getInstance().getCurrentUser() == null)
                Toast.makeText(this, "First authorize!", Toast.LENGTH_LONG).show();
            else replaceFragment(new ChatFragment());
        }
        return super.onOptionsItemSelected(item);
    }
}
