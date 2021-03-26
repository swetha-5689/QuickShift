package com.quickshift.quickshiftbackend.models;

import java.util.List;

public class RequestModel {
    private List<ScheduleRequest> scheduleRequests;
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

    public List<ScheduleRequest> getScheduleRequests() {
        return scheduleRequests;
    }

    public void setScheduleRequests(List<ScheduleRequest> scheduleRequests) {
        this.scheduleRequests = scheduleRequests;
    }

    public RequestModel() {
    }

    public RequestModel(List<ScheduleRequest> scheduleRequests, List<Practitioner> practitionerList, List<Timeslot> timeslots) {
        this.scheduleRequests = scheduleRequests;
        this.practitioners = practitionerList;
        this.timeslots = timeslots;
    }
}
