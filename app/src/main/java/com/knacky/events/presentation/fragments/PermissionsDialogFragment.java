package com.knacky.events.presentation.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import android.support.v4.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.knacky.events.R;
import com.knacky.events.presentation.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by knacky on 07.05.2018.
 */

public class PermissionsDialogFragment extends DialogFragment {
    @BindView(R.id.skipPermsDialogLayout)
    LinearLayout skipPermissionsBtn;

    PermissionsDialogFragmentListener permissionsDialogFragmentListener;

    public interface PermissionsDialogFragmentListener{
        void onAuthirizedUserLoggedIn();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View permissionsFragmentview = inflater.inflate(R.layout.dialog_user_permissions, null, false);
        ButterKnife.bind(this, permissionsFragmentview);
        permissionsDialogFragmentListener = (MainActivity) getActivity();


        skipPermissionsBtn.setOnClickListener(v -> {
            dismiss();
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                permissionsDialogFragmentListener.onAuthirizedUserLoggedIn();
                Log.i("SignIn", "Current user is: " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

            } else {
                Log.i("SignIn", "You are not Authorized!");
                (new SignInDialogFragment()).show(getFragmentManager(), "signInDialogFragment");
            }
        });


        return permissionsFragmentview;
    }

}
