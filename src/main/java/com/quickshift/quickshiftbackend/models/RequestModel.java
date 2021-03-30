package com.quickshift.quickshiftbackend.models;

import java.util.List;

public class RequestModel {
    private List<RequestOff> requestOffs;
    private List<Practitioner> practitioners;
    private List<Timeslot> timeslots;

    public List<Practitioner> getPractitioners() {
        return practitioners;
    }

    public void setPractitioners(List<Practitioner> practitioners) {
        this.practitioners = practitioners;
    }

    public List<Timeslot> getTimeslots() {
        return timeslots;
    }

    public void setTimeslots(List<Timeslot> timeslots) {
        this.timeslots = timeslots;
    }

    public List<RequestOff> getRequestOffs() {
        return requestOffs;
    }

    public void setRequestOffs(List<RequestOff> requestOffs) {
        this.requestOffs = requestOffs;
    }

    public RequestModel() {
    }

    public RequestModel(List<RequestOff> requestOffs, List<Practitioner> practitionerList, List<Timeslot> timeslots) {
        this.requestOffs = requestOffs;
        this.practitioners = practitionerList;
        this.timeslots = timeslots;
    }
}
