package com.knacky.events.presentation.fragments;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.knacky.events.R;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by knacky on 02.03.2018.
 */

public class SignInDialogFragment extends DialogFragment {
    @BindView(R.id.sign_in_google_btn)
    Button signInGoogleBtn;

    @BindView(R.id.signUpNowLayout)
    LinearLayout signUpNowBtn;

    @BindView(R.id.logInBtn)
    Button logInBtn;

    @BindView(R.id.skipLayout)
    LinearLayout skipAuthBtn;

    @BindView(R.id.sign_in_FB_button)
    LoginButton signInFBbtn;

    SignUpDialogFragment signUpDialogFragment;
    private static final int RC_GOOGLE_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager mCallbackManager;       //for facebook sign in

    private static final String TAG = "SignInDialogFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View signInFragmentview = inflater.inflate(R.layout.dialog_auth_sign_in_layout, null, false);
        ButterKnife.bind(this, signInFragmentview);

        signUpDialogFragment = new SignUpDialogFragment();
        signInFBbtn.setFragment(this);      //-----------------------REQUIRED LINE FOR FRAGMENT !!!
        // initAuthProviders();
//        configureFaceBookSignIn();
//        configureGoogleSignIn();
        mAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();
        initButtons();
        return signInFragmentview;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }

    private void initButtons() {
        signInGoogleBtn.setOnClickListener(v -> {   //googleSignIn button
            signInWithGoogle();
//            configureGoogleSignIn();
        });    //call Sign in with Google
        signInFBbtn.setOnClickListener(v -> {       //FaceBookSignIn button
            Log.d(TAG, "facebook button");
            configureFaceBookSignIn();
        });
        skipAuthBtn.setOnClickListener(v -> dismiss());     //skip Authorization
        signUpNowBtn.setOnClickListener(v -> {      //call Registration form
            dismiss();
            signUpDialogFragment.show(getFragmentManager(), "signUpDialogFragment");
        });
        logInBtn.setOnClickListener(v -> Log.i(TAG, "Current user is: " + mAuth.getCurrentUser().getDisplayName().toString()));
        this.setCancelable(true);
    }



    private void configureFaceBookSignIn() {
        // [START initialize_fblogin]
        // Initialize Facebook Login button
        signInFBbtn.setReadPermissions("email", "public_profile");
        Log.d(TAG, "SetReadPermisions");
        signInFBbtn.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);

                AccessToken token = loginResult.getAccessToken();
                Log.d(TAG, "handleFacebookAccessToken:" + token);

                AuthCredential facebookCredential = FacebookAuthProvider.getCredential(token.getToken());
                signInWithCredential(facebookCredential);           //AUTHENTIFICATION with FaceBook
            }
            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
//                updateUI(null);
            }
            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
//                updateUI(null);
            }
        });
        // [END initialize_fblogin]
    }


    private void signInWithGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN);
    }


    //----------------------------Main action------------------------//
    private void signInWithCredential(AuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //                            updateUI(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                            //                            Snackbar.make(getView().findViewById(R.id.sign_in_dialog_fragment_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            //                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        Toast.makeText(getContext(), "Process completed", Toast.LENGTH_SHORT).show();
//                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }


    // [START onactivityresult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());

                Toast.makeText(getContext(), "Starting process...", Toast.LENGTH_LONG).show();
                //        showProgressDialog();
                AuthCredential googleCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                signInWithCredential(googleCredential);

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
//                updateUI(null);
            }
        }

        // Pass the activity result back to the Facebook SDK
//        if (mCallbackManager != null)
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

    }
    // [END onactivityresult]

    private void signOut() {
        // Firebase sign out
        if (mAuth != null)
            mAuth.signOut();

        // Google sign out
        if (mGoogleSignInClient != null) {
            mGoogleSignInClient.signOut().addOnCompleteListener(getActivity(),
                    new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
//                        updateUI(null);
                        }
                    });
        }
        if (LoginManager.getInstance() != null)
            LoginManager.getInstance().logOut();  //fb sign out
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mAuth.getCurrentUser() != null)
            Log.i(TAG, "Current user is: " + mAuth.getCurrentUser().getDisplayName().toString());
        signOut();

    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}
