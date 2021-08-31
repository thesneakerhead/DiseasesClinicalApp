package com.cz3002.diseasesclinicalapp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ClinicUser extends User {
    public String clinicUID;
   public ClinicUser()
   {
       this.isClinicAcc = true;
   }
}
