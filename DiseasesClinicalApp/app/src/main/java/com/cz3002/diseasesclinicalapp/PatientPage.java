package com.cz3002.diseasesclinicalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ObservableList;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

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
        initPatientPage();
    }
    public void initPatientPage()
    {
        FirebaseDatabaseManager dbMngr = new FirebaseDatabaseManager(PatientPage.this);
        dbMngr.clinicInfos.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<ClinicInfo>>() {
            @Override
            public void onChanged(ObservableList<ClinicInfo> sender) {

            }

            @Override
            public void onItemRangeChanged(ObservableList<ClinicInfo> sender, int positionStart, int itemCount) {

            }

            @Override
            public void onItemRangeInserted(ObservableList<ClinicInfo> sender, int positionStart, int itemCount) {
                Log.d("there was change", dbMngr.clinicInfos.get(0).latLng.get(0).toString());
                Log.d("queue", dbMngr.clinicInfos.get(0).clinicQueue.get(0));
            }

            @Override
            public void onItemRangeMoved(ObservableList<ClinicInfo> sender, int fromPosition, int toPosition, int itemCount) {

            }

            @Override
            public void onItemRangeRemoved(ObservableList<ClinicInfo> sender, int positionStart, int itemCount) {

            }
        });
    }
}