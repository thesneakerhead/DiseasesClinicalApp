package com.cz3002.diseasesclinicalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ClinicPage extends AppCompatActivity {
    private Button nextPatientButton;
    private TextView queueText;
    private FirebaseDatabaseManager dbMngr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clinic_page);
        dbMngr = new FirebaseDatabaseManager(ClinicPage.this);
        HttpRequestHandler hndlr = new HttpRequestHandler();
        Log.d("clinicpage", "thisisclinicpage");
        nextPatientButton = findViewById(R.id.dequeue_button);
        queueText = findViewById(R.id.queue_text);
        listenForQueueChanges("a26df274-c10c-41a0-aec4-38d7d891d966");
        nextPatientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    hndlr.deQueue("a26df274-c10c-41a0-aec4-38d7d891d966")
                            .thenApply(s->{
                                Log.e("the result", s);
                                return null;
                            });
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void listenForQueueChanges(String clinicUID)
    {
        dbMngr.clinicDatabase.getReference("clinicDictionary")
                .child(clinicUID)
                .child("clinicQueue")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getValue()!=null){
                            GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {};
                            ArrayList<String> queue = snapshot.getValue(t);
                            //set display text to number of people in the queue
                            queueText.setText(String.valueOf(queue.size()));
                        }
                        else{
                            queueText.setText("Theres no patients queueing!");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}