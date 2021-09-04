package com.cz3002.diseasesclinicalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import android.os.Bundle;
import android.util.Log;

public class ClinicPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clinic_page);
        Log.d("clinicpage", "thisisclinicpage");


    }
}