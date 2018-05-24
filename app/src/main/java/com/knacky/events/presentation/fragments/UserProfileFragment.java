package com.knacky.events.presentation.fragments;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.knacky.events.R;
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

    @BindView(R.id.back_to_main_btn)
    Button backToMainBtn;

    UserProfileFragmentListener userProfileFragmentListener;

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
//        val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, uri)
//        profilePhotoImg.setImageBitmap(bitmap)
        profilePhotoImg.setImageURI(uri);
    }
}
