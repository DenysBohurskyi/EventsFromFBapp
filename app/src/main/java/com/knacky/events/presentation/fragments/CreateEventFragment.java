package com.knacky.events.presentation.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.knacky.events.R;
import com.knacky.events.data.entities.firebase.EventModelFirebase;
import com.knacky.events.data.repository.FirebaseRepository;
import com.knacky.events.data.repository.FirebaseRepositoryImpl;
import com.knacky.events.extensions.Categories;
import com.knacky.events.presentation.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by knacky on 24.05.2018.
 */
public class CreateEventFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    @BindView(R.id.event_categories_root)
    Spinner ev_categories_spinner;

    @BindView(R.id.cr_event_date_btn_tv)
    TextView date_tv;

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

    public DatePickerDialog.OnDateSetListener onDateSetListener;

    int GALLERY_INTENT_CODE = 1;
    FirebaseRepository firebaseRepository;

    EventModelFirebase eventModelFirebase;
    CreateEventFragmentListener createEventFragmentListener;

    public interface CreateEventFragmentListener {
        void onEventCreated();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_event_fragment, null, false);
        ButterKnife.bind(this, view);

        createEventFragmentListener = (MainActivity) getActivity();
//        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                Log.i("DatePicker", "create event Date set: " + new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year));
//                date_tv.setText(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year));
//            }
//        };

        firebaseRepository = new FirebaseRepositoryImpl();
        eventModelFirebase = new EventModelFirebase("", "", "", "", "", "", FirebaseAuth.getInstance().getUid());

        initCrEventEditFocusListener();
        initCategoriesAdapter();

        return view;
    }

    @OnClick(R.id.confirm_ev_creating_buton)                    //createEvent button
    public void setConfirmEventCreating() {
        if (validateForms()) {
            eventModelFirebase.setCrEventName(nameOfCreatedEvent.getText().toString());
            eventModelFirebase.setCrEventDescription(descriptionOfCreatedEvent.getText().toString());
            eventModelFirebase.setCeEventVenue(venueOfCreatedEvent.getText().toString());

            firebaseRepository.createEvent(eventModelFirebase).subscribe(() -> {
                createEventFragmentListener.onEventCreated();
                Log.d("createEvent", "Event created");
                Toast.makeText(getContext(), "Захід створено!", Toast.LENGTH_LONG).show();
            });

        } else
            Toast.makeText(getContext(), "Заповніть правильно всі поля!", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.cr_event_date_btn_tv)
    public void chooseDate() {
        Bundle bundle = new Bundle();
        bundle.putInt("DATE", 3);

        DialogFragment newFragment = new DatePickerFragment();
        newFragment.setArguments(bundle);


        newFragment.show(getFragmentManager(), "datePicker");
    }

    private boolean validateForms() {
        if (nameOfCreatedEvent.getText().toString().equals("") || descriptionOfCreatedEvent.getText().equals("") || venueOfCreatedEvent.getText().equals(""))
            return false;
        else if (eventModelFirebase.getCrEventImgUri().equals("")) {
            Toast.makeText(getContext(), "Виберіть фото!", Toast.LENGTH_LONG).show();
            return false;
//        } else if (eventModelFirebase.getCrEventDate().equals("")) {
////            Toast.makeText(getContext(), "Вкажіть дату!", Toast.LENGTH_LONG).show();
//            return false;
        } else if (eventModelFirebase.getCrEventCatecory().equals("")) {
//            Toast.makeText(getContext(), "Вкажіть категорію!", Toast.LENGTH_LONG).show();
            return false;
        } else return true;
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

    /**
     * set imgate to the View and set imageUri to the model
     */

    public void setImageFromUri(Uri uri) {
        firebaseRepository.upload(uri, "users").subscribe(photoUrl -> {
//            profilePhotoImg.setImageURI(it);
            Glide
                    .with(getContext())
                    .load(photoUrl)
                    .into(pictOfCreatedEvent);

            eventModelFirebase.setCrEventImgUri(photoUrl.toString());
            Log.d("createEvent", "setImage succeed, it: " + photoUrl.toString());
        }, error -> {
            Log.d("createEvent", "setImage error, it: " + error.toString());
        });
//            pictOfCreatedEvent.Uri
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

    /**
     * set category to the model
     */
    public void initCategoriesAdapter() {
        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, Categories.createEventCategories);

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
                if (position != 0)
                    eventModelFirebase.setCrEventCatecory(ev_categories_spinner.getSelectedItem().toString());
                // показываем позиция нажатого элемента
//                Toast.makeText(getContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    /**
     * set date to the model
     */
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

        Log.i("DatePicker", "create event Date set: " + new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year));
//        date_tv.setText(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year));

//        eventModelFirebase.setCrEventDate("awdawda");
    }
}


