package com.cz3002.diseasesclinicalapp;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatientUser extends User {
    public ArrayList<SymptomCard> symptomCards;
    public PatientUser()
    {
        this.isClinicAcc = false;
    }
    public void addSymptomCard(SymptomCard symptomCard)
    {
        if(this.symptomCards.isEmpty())
        {
            this.symptomCards = new ArrayList<SymptomCard>();
            this.symptomCards.add(symptomCard);
        }
        else
        {
            this.symptomCards.add(symptomCard);
        }
    }

}
