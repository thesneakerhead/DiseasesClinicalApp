package com.cz3002.diseasesclinicalapp;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDatabaseManager {
    public FirebaseDatabase instatiateAppDatabase(Context context)
    {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId("1:303895926741:android:aca9bd622ce5c5b5eb0f5c")
                .setApiKey("AIzaSyBMGgbLppI7TeD2vp-CKASPalrlyqDENTs")
                .setDatabaseUrl("https://ase-clinic-default-rtdb.asia-southeast1.firebasedatabase.app")
                .build();
        FirebaseApp.initializeApp(context, options,"appDB");
        FirebaseApp initApp = FirebaseApp.getInstance("appDB");
        FirebaseDatabase appDatabase = FirebaseDatabase.getInstance(initApp);
        return appDatabase;
    }
    public FirebaseDatabase instantiateClinicDatabase(Context context)
    {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId("1:561455279142:android:bac250ac7ac82919d74ae7")
                .setApiKey("AIzaSyCGhi0cxS-VvZTHQlRtTp3tyl4so1kLc_g")
                .setDatabaseUrl("https://clinicaldatabase-49662-default-rtdb.asia-southeast1.firebasedatabase.app")
                .build();
        FirebaseApp.initializeApp(context, options,"clinicDB");
        FirebaseApp initApp = FirebaseApp.getInstance("clinicDB");
        FirebaseDatabase ClinicDatabase = FirebaseDatabase.getInstance(initApp);
        return ClinicDatabase;
    }
}
