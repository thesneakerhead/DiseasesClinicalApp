package com.cz3002.diseasesclinicalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PatientPage extends AppCompatActivity {
    FirebaseDatabaseManager dbMngr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_page);
        dbMngr = new FirebaseDatabaseManager();

        FirebaseDatabase appDatabase= dbMngr.instatiateAppDatabase(PatientPage.this);
        FirebaseDatabase clinicDatabase = dbMngr.instantiateClinicDatabase(PatientPage.this);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        appDatabase.getReference("Users").child(user.getUid()).setValue("test");
        clinicDatabase.getReference("Users").child(user.getUid()).setValue("test");

    }
}