package com.cz3002.diseasesclinicalapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.firebase.ui.auth.util.ExtraConstants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;

public class FirebaseLogin extends AppCompatActivity {

    private static int AUTH_REQUEST_CODE = 7192;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener listener;
    private List<AuthUI.IdpConfig> providers;
    private Button signoutbutton;
    private TextView idText;
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
    );
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(listener!=null)firebaseAuth.removeAuthStateListener(listener);
        super.onStop();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.firebaselogin);
        init();
        signoutbutton = (Button)findViewById(R.id.signout_button);
        idText = (TextView) findViewById(R.id.idText) ;
        signoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance()
                        .signOut(FirebaseLogin.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                // ...
                                createSignInPage();
                            }
                        });
            }
        });
        if (AuthUI.canHandleIntent(getIntent())) {
            if (getIntent().getExtras() == null) {
                return;
            }
            String link = getIntent().getExtras().getString(ExtraConstants.EMAIL_LINK_SIGN_IN);
            if (link != null) {
                Intent signInIntent = AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setEmailLink(link)
                        .setAvailableProviders(providers)
                        .build();
                signInLauncher.launch(signInIntent);
            }
        }
    }

    private void init() {
        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //Get user
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    Toast.makeText(FirebaseLogin.this, "There is an account logged in: " + user.getUid(), Toast.LENGTH_SHORT).show();
                } else {
                    createSignInPage();
                }}
        };
        firebaseAuth.addAuthStateListener(listener);
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        CallbackManager mCallbackManager = CallbackManager.Factory.create();
//        mCallbackManager.onActivityResult(requestCode, resultCode, data);
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Pass the activity result back to the Facebook SDK
//
//    }
    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {

        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            idText.setText(user.getUid());
            Intent i = new Intent(FirebaseLogin.this,PatientPage.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        } else {

        }
    }
    public void createSignInPage()
    {

        providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build(), // Log in using Google
                new AuthUI.IdpConfig.EmailBuilder().build(), // Log in using Email
                new AuthUI.IdpConfig.FacebookBuilder().build(), // Log in using Facebook
                new AuthUI.IdpConfig.PhoneBuilder().build() //Log in using Phone
        );
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build();
        signInLauncher.launch(signInIntent);

    }
}