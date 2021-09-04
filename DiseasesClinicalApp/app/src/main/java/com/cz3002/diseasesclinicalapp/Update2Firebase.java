package com.cz3002.diseasesclinicalapp;

import android.content.Context;
import android.database.Observable;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.UUID;
import java.util.AbstractCollection.*;

//to be deleted later, here just to upload stuff to clinic/patient database
public class Update2Firebase {

    public void upload()
    {

    }
    public String generateUUID()
    {
        return UUID.randomUUID().toString();
    }

    // stores clinicUUID,clinicName key,value pair in clinicDictionary and uploads to firebase
    // returns the clinicUUID
    public String uploadClinicsToFirebase(ClinicInfo clinicInfo,Context context)
    {
        FirebaseDatabaseManager dbManager = new FirebaseDatabaseManager(context);
        String clinicUUID = generateUUID();
        
        dbManager.clinicDatabase.getReference("clinicDictionary").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue()!=null)
                {
                    GenericTypeIndicator<HashMap<String,ClinicInfo>> t = new GenericTypeIndicator<HashMap<String, ClinicInfo>>() {
                    };
                    HashMap<String,ClinicInfo> prevClinicDict = snapshot.getValue(t);
                    prevClinicDict.put(clinicUUID,clinicInfo);
                    dbManager.clinicDatabase.getReference("clinicDictionary").setValue(prevClinicDict);

                }
                else {
                    HashMap<String, ClinicInfo> newClinicDictionary = new HashMap<String, ClinicInfo>();
                    newClinicDictionary.put(clinicUUID,clinicInfo);
                    dbManager.clinicDatabase.getReference("clinicDictionary").setValue(newClinicDictionary);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return clinicUUID;
    }

}
