package com.knacky.events.presentation.fragments;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.knacky.events.R;
import com.knacky.events.data.entities.firebase.UserModel;
import com.knacky.events.data.repository.FirebaseRepository;
import com.knacky.events.data.repository.FirebaseRepositoryImpl;
import com.knacky.events.extensions.Prefs;
import com.knacky.events.presentation.MainActivity;
import com.knacky.events.presentation.presenters.EventPagePresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by knacky on 24.05.2018.
 */
public class UserProfileFragment extends Fragment {
    @BindView(R.id.add_profile_photo_image)
    CircleImageView profilePhotoImg;

    @BindView(R.id.name_edit)
    EditText profileName;

    @BindView(R.id.email_edit)
    EditText profileEmail;

    @BindView(R.id.pass_edit)
    EditText profilePass;

    @BindView(R.id.back_to_main_btn)
    Button backToMainBtn;

    UserModel userModel;
    UserProfileFragmentListener userProfileFragmentListener;
    FirebaseRepository firebaseRepository = new FirebaseRepositoryImpl();

    public static int GALLERY_INTENT_CODE = 1;

    public interface UserProfileFragmentListener {
        void onBackToMainPageBtnClicked();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_page, null, false);

        ButterKnife.bind(this, view);

        userProfileFragmentListener = (MainActivity) getActivity();

        userModel = new UserModel();
        setFields();
//        eventPagePresenter = new EventPagePresenterImpl(getContext());
//        eventPagePresenter.setEventPageView(this);


//        initButtons();
        return view;
    }

    @OnClick(R.id.add_profile_photo_image)
    public void onAddPhotoClick() {
        openGallery();
    }

    @OnClick(R.id.back_to_main_btn)
    public void onBackToMainClicked() {
        userProfileFragmentListener.onBackToMainPageBtnClicked();
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

//        profilePhotoImg.setImageURI(uri);

//        firebaseRepository.uploadFile(uri, "users");

        firebaseRepository.upload(uri, "users").subscribe(photoUrl -> {
//            profilePhotoImg.setImageURI(it);
            Glide
                    .with(getContext())
                    .load(photoUrl)
                    .into(profilePhotoImg);

            Log.d("onImageUpload", "setImage succeed, it: " + photoUrl.toString());
        }, error -> {
            Log.d("onImageUpload", "setImage error, it: " + error.toString());
        });
//                .doOnSubscribe(()->{
//            Log.d("onImageUpload", "succeed, it: " );
//        });
    }

    private void setFields() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(Prefs.getUser(getContext()));
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        userModel.setProfileImgUrl(dataSnapshot.getValue(UserModel.class).getProfileImgUrl());
                        userModel.setFullName(dataSnapshot.getValue(UserModel.class).getFullName());
                        userModel.setEmail(dataSnapshot.getValue(UserModel.class).getEmail());
                        userModel.setPassword(dataSnapshot.getValue(UserModel.class).getPassword());

                        profileName.setText(userModel.getFullName());
                        Glide
                                .with(getContext())
                                .load(userModel.getProfileImgUrl())
                                .into(profilePhotoImg);

                        profileEmail.setText(userModel.getEmail());
                        profilePass.setText(userModel.getPassword());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }

}

