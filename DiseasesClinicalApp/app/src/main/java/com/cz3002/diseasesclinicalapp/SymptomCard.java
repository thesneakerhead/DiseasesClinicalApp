package com.cz3002.diseasesclinicalapp;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SymptomCard {
    public ArrayList<String> symptoms;
    public String clinicUID;
    public String date;
    public String status;//ONGOING,COMPLETED or CANCELLED
}


