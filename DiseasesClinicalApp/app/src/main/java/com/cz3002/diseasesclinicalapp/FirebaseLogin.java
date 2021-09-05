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
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.firebase.ui.auth.util.ExtraConstants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.SneakyThrows;

public class FirebaseLogin extends AppCompatActivity {

    private static int AUTH_REQUEST_CODE = 7192;
    private FirebaseDatabaseManager dbMngr;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener listener;
    private List<AuthUI.IdpConfig> providers;
    private Button signoutbutton;
    private TextView idText;
    private List<FirebaseApp> appList;
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @SneakyThrows
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

    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();
        dbMngr = new FirebaseDatabaseManager(FirebaseLogin.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firebaselogin);
        init();
        signoutbutton = (Button)findViewById(R.id.signout_button);
        idText = (TextView) findViewById(R.id.idText) ;
        signoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               signOut();
               createSignInPage();
            }
        });

    }

    private void init() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Toast.makeText(FirebaseLogin.this, "There is an account logged in: " + user.getUid(), Toast.LENGTH_SHORT).show();
        } else {
            createSignInPage();
        }

    }
    private void signOut()
    {
        //unmounting the databases when logging out
        AuthUI.getInstance()
                .signOut(FirebaseLogin.this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        unmountDatabases();
                    }
                });
    }
    public void unmountDatabases()
    {
        dbMngr.appDBApp.delete();
        dbMngr.clinicDBApp.delete();
    }







    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) throws JsonProcessingException {

        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();
            idText.setText(user.getUid());
            //check if account is new/clinic/patient account
            handleAccount(user.getUid());
        } else {

        }
    }

    private void handleAccount(String uid) {

        DatabaseReference dbRef = dbMngr.appDatabase.getReference("Users").child(uid);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue()!=null)
                {
                    Log.d("userexists", "This is not the first time signing in");
                    GenericTypeIndicator<User> t = new GenericTypeIndicator<User>(){};
                    User loggedInUser = snapshot.getValue(t);
                    if(loggedInUser.isClinicAcc==true)
                    {
                        //unmountDatabases();
                        Intent i = new Intent(FirebaseLogin.this,ClinicPage.class);
                        startActivity(i);
                    }
                    if(loggedInUser.isClinicAcc==false)
                    {
                        //unmountDatabases();
                        Intent i = new Intent(FirebaseLogin.this,PatientPage.class);
                        startActivity(i);
                    }

                }
                else
                {
                    Log.d("usernotexists", "First time user is signing in, creating data for user");
                    //assuming only patients register through the app
                    PatientUser newUser  = new PatientUser();
                    dbRef.setValue(newUser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("checkfailed", "Account check failed!");
            }
        });
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
    //to be deleted, used to upload clinic info.
    public void uploadClinicInfo()
    {
        Update2Firebase update = new Update2Firebase();
        ClinicInfo clinicInfo = new ClinicInfo();
        ArrayList<Double> Latlng = new ArrayList<Double>();
        Latlng.add(103.872312298306994);
        Latlng.add(1.36995099695439);
        clinicInfo.setClinicName("Changi General Hospital");
        clinicInfo.setLatLng(Latlng);
        update.uploadClinicsToFirebase(clinicInfo,FirebaseLogin.this);
    }
}