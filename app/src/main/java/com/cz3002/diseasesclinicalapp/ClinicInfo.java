package com.cz3002.diseasesclinicalapp;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClinicInfo {
    public String clinicName;
    public ArrayList<String> clinicQueue;
    public ArrayList<Double> latLng;
}
