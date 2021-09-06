package com.cz3002.diseasesclinicalapp;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class FirebaseDatabaseManager {
    public FirebaseApp appDBApp;
    public FirebaseApp clinicDBApp;
    public FirebaseDatabase appDatabase;
    public FirebaseDatabase clinicDatabase;
    public ObservableArrayList<ClinicInfo> clinicInfos;
    public FirebaseDatabaseManager(Context context)
    {
        clinicInfos = new ObservableArrayList<ClinicInfo>();
        try
        {
            this.appDBApp=FirebaseApp.getInstance("appDB");
            this.clinicDBApp = FirebaseApp.getInstance("clinicDB");
            this.appDatabase = FirebaseDatabase.getInstance(this.appDBApp);
            this.clinicDatabase=FirebaseDatabase.getInstance(this.clinicDBApp);
            getAllClinicInfo();
        }
        catch(IllegalStateException e)
        {
            instatiateAppDatabase(context);
            instantiateClinicDatabase(context);
            getAllClinicInfo();
        }


    }


    public void instatiateAppDatabase(Context context)
    {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId("1:303895926741:android:255d85c415995442eb0f5c")
                .setApiKey("AIzaSyBMGgbLppI7TeD2vp-CKASPalrlyqDENTs")
                .setDatabaseUrl("https://ase-clinic-default-rtdb.asia-southeast1.firebasedatabase.app")
                .build();
        FirebaseApp.initializeApp(context, options,"appDB");
        FirebaseApp initApp = FirebaseApp.getInstance("appDB");
        this.appDBApp=initApp;
        this.appDatabase = FirebaseDatabase.getInstance(initApp);
    }
    public void instantiateClinicDatabase(Context context)
    {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId("1:561455279142:android:bac250ac7ac82919d74ae7")
                .setApiKey("AIzaSyCGhi0cxS-VvZTHQlRtTp3tyl4so1kLc_g")
                .setDatabaseUrl("https://clinicaldatabase-49662-default-rtdb.asia-southeast1.firebasedatabase.app")
                .build();
        FirebaseApp.initializeApp(context, options,"clinicDB");
        FirebaseApp initApp = FirebaseApp.getInstance("clinicDB");
        this.clinicDBApp=initApp;
        this.clinicDatabase = FirebaseDatabase.getInstance(initApp);
    }
    public void getAllClinicInfo(){

        this.clinicDatabase.getReference("clinicDictionary").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue()!=null) {
                    HashMap<String, Object> clinics = (HashMap<String, Object>) snapshot.getValue();
                    for (HashMap.Entry<String, Object> entry : clinics.entrySet()) {
                       Map singleClinic = (Map)entry.getValue();
                        Log.d("clinicname", (String)singleClinic.get("clinicName"));
                       ClinicInfo singleClinicInfo = new ClinicInfo((String)singleClinic.get("clinicName"),(ArrayList<String>)singleClinic.get("clinicQueue"),(ArrayList<Double>)singleClinic.get("latLng"));
                       clinicInfos.add(singleClinicInfo);
                    }
                }
                Log.d("all info", clinicInfos.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }


}
