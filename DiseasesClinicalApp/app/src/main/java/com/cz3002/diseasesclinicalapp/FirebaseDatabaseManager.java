package com.cz3002.diseasesclinicalapp;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDatabaseManager {
    public FirebaseApp appDBApp;
    public FirebaseApp clinicDBApp;
    public FirebaseDatabase appDatabase;
    public FirebaseDatabase clinicDatabase;
    public FirebaseDatabaseManager(Context context)
    {
        instatiateAppDatabase(context);
        instantiateClinicDatabase(context);
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
}
