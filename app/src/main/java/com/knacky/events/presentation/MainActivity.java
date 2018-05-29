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
import com.knacky.events.data.repository.FirebaseRepository;
import com.knacky.events.data.repository.FirebaseRepositoryImpl;
import com.knacky.events.extensions.Prefs;
import com.knacky.events.presentation.fragments.ChatFragment;
import com.knacky.events.presentation.fragments.CreateEventFragment;
import com.knacky.events.presentation.fragments.EventPageFragment;
import com.knacky.events.presentation.fragments.EventsListFragment;
import com.knacky.events.presentation.fragments.PermissionsDialogFragment;
import com.knacky.events.presentation.fragments.ReportFragment;
import com.knacky.events.presentation.fragments.SignInDialogFragment;
import com.knacky.events.presentation.fragments.SignUpDialogFragment;
import com.knacky.events.presentation.fragments.UserProfileFragment;

public class MainActivity extends AppCompatActivity implements EventsListFragment.EventListFragmentListener,
        SignInDialogFragment.SignInDialogFragmentListener,
        UserProfileFragment.UserProfileFragmentListener,
        SignUpDialogFragment.SignUpDialogFragmentListener,
        CreateEventFragment.CreateEventFragmentListener,
        PermissionsDialogFragment.PermissionsDialogFragmentListener{
    EventsListFragment eventsListFragment;
    UserProfileFragment userProfileFragment;
    SignInDialogFragment signInDialogFragment;
    //    FirebaseRepository firebaseRepository;

    public static String NO_IMAGE_AVALIABLE = "https://firebasestorage.googleapis.com/v0/b/events-knacky.appspot.com/o/no_image_available.jpg?alt=media&token=21ac0c1c-d8c9-4f89-907f-3f094370d418";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eventsListFragment = new EventsListFragment();
        userProfileFragment = new UserProfileFragment();
        signInDialogFragment = new SignInDialogFragment();
//        firebaseRepository = new FirebaseRepositoryImpl();

        (new PermissionsDialogFragment()).show(getSupportFragmentManager(), "signUpDialogFragment");
        //=======================
//        replaceFragment(eventsListFragment);
        //=======================
//        getActionBar().setHomeButtonEnabled(true);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) Toast.makeText(this, "Hello, " +
                FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), Toast.LENGTH_LONG).show();
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
    public void onEventCreated() {replaceFragment(eventsListFragment);}

    @Override
    public void onAuthirizedUserLoggedIn() {replaceFragment(eventsListFragment);}

    @Override
    public void onEventItemClick(String id) {

        Log.i("onClick", "ev id: " + id);
        replaceFragment(EventPageFragment.newInstance(id));
    }

    @Override
    public void onCreateEventBtnClicked() {
        replaceFragment(new CreateEventFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            if (item.getItemId() == R.id.settings_menu_item)
                replaceFragment(new UserProfileFragment());
            if (item.getItemId() == R.id.report_menu_item)
                replaceFragment(new ReportFragment());
            if (item.getItemId() == R.id.chat_item)
                replaceFragment(new ChatFragment());
            if (item.getItemId() == R.id.account_item)
                replaceFragment(new UserProfileFragment());
            if (item.getItemId() == R.id.sign_out_menu_item) {
                Prefs.deleteUser(FirebaseAuth.getInstance().getCurrentUser().getUid(), this);
                signInDialogFragment.signOut();
                signInDialogFragment.show(getSupportFragmentManager(), "signInDialogFragment");
            }
        } else if (item.getItemId() == R.id.account_item) {
            signInDialogFragment.show(getSupportFragmentManager(), "signInDialogFragment");
        } else Toast.makeText(this, "First authorize!", Toast.LENGTH_LONG).show();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLogIn() {
        replaceFragment(userProfileFragment);
    }

    @Override
    public void onBackToMainPageBtnClicked() {
        replaceFragment(eventsListFragment);
    }

    @Override
    public void onConfirmClicked() {
        replaceFragment(userProfileFragment);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        signInDialogFragment.signOut();
    }


}
