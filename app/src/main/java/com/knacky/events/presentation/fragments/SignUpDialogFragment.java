package com.knacky.events.presentation.fragments;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.knacky.events.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by knacky on 02.03.2018.
 */

public class SignUpDialogFragment extends DialogFragment {
    @BindView(R.id.sign_up_FirstLastNameEditText)
    EditText signUpFirstNameEditText;

    @BindView(R.id.sign_up_email_EditText)
    EditText signUpEmailEditText;

    @BindView(R.id.sign_up_password_EditText)
    EditText signUpPasswordEditText;

    @BindView(R.id.sign_up_confirmBtn)
    Button signUpConfirmButtonl;

    @BindView(R.id.sign_up_skipLayout)
    LinearLayout signUpSkipBtn;

    private FirebaseAuth mAuth;

    private static final String TAG = "SignInDialogFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View signUpFragmentview = inflater.inflate(R.layout.dialog_auth_sign_up_layout, null, false);
        ButterKnife.bind(this, signUpFragmentview);
        mAuth = FirebaseAuth.getInstance();
        initButton();
        return signUpFragmentview;
    }

    private void initButton() {
        signUpSkipBtn.setOnClickListener(v -> dismiss());
        signUpConfirmButtonl.setOnClickListener(v -> createAccount(
                signUpEmailEditText.getText().toString(),
                signUpPasswordEditText.getText().toString(),
                signUpFirstNameEditText.getText().toString()))
        ;
        this.setCancelable(true);
    }


    private void createAccount(String email, String password, String lastFirstName) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }
//        showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            setUserDisplayedName(lastFirstName);
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }
//                        hideProgressDialog();
                    }
                });
        // [END create_user_with_email]
    }

    private void setUserDisplayedName(String dispName) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(dispName).build();
        FirebaseAuth.getInstance().getCurrentUser().updateProfile(profileUpdates);
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = signUpEmailEditText.getText().toString();
        if (TextUtils.isEmpty(email)) {
            signUpEmailEditText.setError("Required.");
            valid = false;
        } else {
            signUpEmailEditText.setError(null);
        }

        String password = signUpPasswordEditText.getText().toString();
        if (TextUtils.isEmpty(password)) {
            signUpEmailEditText.setError("Required.");
            valid = false;
        } else {
            signUpEmailEditText.setError(null);
        }

        String firstLastName = signUpFirstNameEditText.getText().toString();
        if (TextUtils.isEmpty(firstLastName)) {
            signUpFirstNameEditText.setError("Required.");
            valid = false;
        } else {
            signUpFirstNameEditText.setError(null);
        }

        return valid;
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
