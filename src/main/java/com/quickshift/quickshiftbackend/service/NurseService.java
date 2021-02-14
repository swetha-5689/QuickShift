package com.quickshift.quickshiftbackend.service;

import com.quickshift.quickshiftbackend.models.Nurse;

import java.util.ArrayList;
import java.util.Arrays;

public class NurseService {
    private ArrayList<Nurse> nurseList = new ArrayList<>(Arrays.asList(new Nurse("Param Patel"),
            new Nurse("Neha Nelcon"),
            new Nurse("Swagtha Angara"),
            new Nurse("Brooke Getter"),
            new Nurse("Ariela Chomski"),
            new Nurse("Hana Godrich"),
            new Nurse("Robert Brokaw"),
            new Nurse("Adam Novak"),
            new Nurse("Suraj Sanyal"),
            new Nurse("Aniqa Rahim"),
            new Nurse("Heather Wun"),
            new Nurse("Chris Rosenberger"),
            new Nurse("Disha Bailoor")));

    public void setNurseList(ArrayList<Nurse> nurseList) {
        this.nurseList = nurseList;
    }

    public ArrayList<Nurse> getNurseList() {
        return this.nurseList;
    }

    public NurseService(ArrayList<Nurse> nurseList) {
        this.nurseList = nurseList;
    }

    public Nurse getNurseById(String id) {
        Nurse nurse = nurseList.get(0);
        for(Nurse n : nurseList) if (n.getShortId().equals(id)) return n;
        return nurse;
    }

    public Nurse addNurse(String name) {
        Nurse newNurse = new Nurse(name);
        nurseList.add(newNurse);
        return newNurse;
    }

    public boolean removeNurse(String id) {
        Nurse nurse = nurseList.get(0);
        for(Nurse n : nurseList) if (n.getShortId().equals(id)) {
            return nurseList.remove(n);
        }
        return false;
    }

    public NurseService() {
    }


}
