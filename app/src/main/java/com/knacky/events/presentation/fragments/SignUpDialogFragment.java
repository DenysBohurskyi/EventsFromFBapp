package com.knacky.events.presentation.fragments;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.facebook.login.widget.LoginButton;
import com.knacky.events.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by knacky on 02.03.2018.
 */

public class SignUpDialogFragment extends DialogFragment {
    @BindView(R.id.sign_up_FirstNameEditText)
    EditText signUpFirstNameEditText;

    @BindView(R.id.sign_up_LastName_EditText)
    EditText signUpLastNameEditText;

    @BindView(R.id.sign_up_email_EditText)
    EditText signUpEmailEditText;

    @BindView(R.id.sign_up_password_EditText)
    EditText signUpPasswordEditText;

    @BindView(R.id.sign_up_confirmBtn)
    Button signUpConfirmButtonl;

    @BindView(R.id.sign_up_skipLayout)
    LinearLayout signUpSkipBtn;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View signUpFragmentview = inflater.inflate(R.layout.dialog_auth_sign_up_layout, null, false);
        ButterKnife.bind(this, signUpFragmentview);

        initButton();
        return signUpFragmentview;
    }

    private void initButton() {
        signUpSkipBtn.setOnClickListener(v -> dismiss());

        this.setCancelable(true);
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}
