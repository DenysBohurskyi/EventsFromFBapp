package com.knacky.events.presentation.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.knacky.events.R;
import com.knacky.events.data.entities.firebase.EventModelFirebase;
import com.knacky.events.data.repository.FirebaseRepository;
import com.knacky.events.data.repository.FirebaseRepositoryImpl;
import com.knacky.events.extensions.Categories;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by knacky on 24.05.2018.
 */
public class CreateEventFragment extends Fragment {
    @BindView(R.id.event_categories_root)
    Spinner ev_categories_spinner;


    @BindView(R.id.event_name_input_layout)
    TextInputLayout nameOfCreatedEventLayout;

    @BindView(R.id.event_description_input_layout)
    TextInputLayout descriptionOfCreatedEventLayout;

    @BindView(R.id.event_venue_input_layout)
    TextInputLayout venueOfCreatedEventLayout;

    @BindView(R.id.add_event_pict_imageview)
    ImageView pictOfCreatedEvent;

    @BindView(R.id.event_name_edit)
    EditText nameOfCreatedEvent;

    @BindView(R.id.event_description_edit)
    EditText descriptionOfCreatedEvent;

    @BindView(R.id.event_venue_edit)
    EditText venueOfCreatedEvent;

    @BindView(R.id.confirm_ev_creating_buton)
    Button confirmEventCreating;

    int GALLERY_INTENT_CODE = 1;
    FirebaseRepository firebaseRepository;

    EventModelFirebase eventModelFirebase;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_event_fragment, null, false);
        firebaseRepository = new FirebaseRepositoryImpl();
        ButterKnife.bind(this, view);

        initCrEventEditFocusListener();
        initCategoriesAdapter();
        eventModelFirebase = new EventModelFirebase("", "", "", "", FirebaseAuth.getInstance().getUid());

        return view;
    }

    @OnClick(R.id.confirm_ev_creating_buton)                    //createEvent button
    public void setConfirmEventCreating() {
        if (validateForms()) {
            eventModelFirebase.setCrEventName(nameOfCreatedEvent.getText().toString());
            eventModelFirebase.setCrEventDescription(descriptionOfCreatedEvent.getText().toString());
            eventModelFirebase.setCeEventVenue(venueOfCreatedEvent.getText().toString());

            firebaseRepository.createEvent(eventModelFirebase)
                    .doOnCompleted(() -> {
                        Log.d("createEvent", "Event created");
                        Toast.makeText(getContext(), "Захід створено!", Toast.LENGTH_LONG).show();
                    });

        } else Toast.makeText(getContext(), "Правильно заповніть поля!", Toast.LENGTH_LONG).show();
    }

    private boolean validateForms() {
        if (nameOfCreatedEvent.getText() != null || descriptionOfCreatedEvent.getText() != null | venueOfCreatedEvent.getText() != null)
            return true;
        if (eventModelFirebase.getCrEventImgUri().equals("")) {
            Toast.makeText(getContext(), "Виберіть фото!", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }

    @OnClick(R.id.add_event_pict_imageview)
    public void addCreatingEvPict() {
        openGallery();
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select File"), GALLERY_INTENT_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY_INTENT_CODE && data != null) {
                setImageFromUri(data.getData());
            }
        }
    }

    public void setImageFromUri(Uri uri) {

        firebaseRepository.upload(uri, "users").subscribe(photoUrl -> {
//            profilePhotoImg.setImageURI(it);
            Glide
                    .with(getContext())
                    .load(photoUrl)
                    .into(pictOfCreatedEvent);
            Log.d("createEvent", "setImage succeed, it: " + photoUrl.toString());
        }, error -> {
            Log.d("createEvent", "setImage error, it: " + error.toString());
        });
//            pictOfCreatedEvent.Uri
    }

    private void ooooo() {
        nameOfCreatedEventLayout.setError("опис");
    }

    private void initCrEventEditFocusListener() {
        nameOfCreatedEvent.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                Log.d("createEvent", "name field has focus, name: " + "\"" + nameOfCreatedEvent.getText().toString() + "\"");
//                nameOfCreatedEventLayout.setErrorEnabled(false);
                nameOfCreatedEventLayout.setError(null);
            } else {
                Log.d("createEvent", "has not focus1");

                if (nameOfCreatedEvent.getText().toString().equals("")) {
//                    nameOfCreatedEventLayout.setError("it can not be empty");
                    ooooo();
                    nameOfCreatedEvent.setError("it can not be empty");
                    Log.d("createEvent", "has not focus2");
                }
                descriptionOfCreatedEventLayout.setError(null);
            }
        });

        descriptionOfCreatedEvent.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                nameOfCreatedEventLayout.setError(null);
            } else {

                if (descriptionOfCreatedEvent.getText().toString().equals("")) {
                    descriptionOfCreatedEventLayout.setError("опис не може бути пустим");
                } else descriptionOfCreatedEventLayout.setError(null);
            }
        });

        venueOfCreatedEvent.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                descriptionOfCreatedEventLayout.setError(null);
            } else {

                if (venueOfCreatedEvent.getText().toString().equals("")) {
                    descriptionOfCreatedEventLayout.setErrorEnabled(false);
                    venueOfCreatedEventLayout.setError("вкажіть адресу!");
                } else descriptionOfCreatedEventLayout.setError(null);
            }
        });

    }

    public void initCategoriesAdapter() {
        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, Categories.eventCategories);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ev_categories_spinner.setAdapter(adapter);
        // заголовок
        ev_categories_spinner.setPrompt(getResources().getString(R.string.event_categories));
        // выделяем элемент
        ev_categories_spinner.setSelection(0);
        // устанавливаем обработчик нажатия
        ev_categories_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                Toast.makeText(getContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }
}


