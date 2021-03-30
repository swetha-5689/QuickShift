package com.quickshift.quickshiftbackend.models;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity
public class Schedule {
    Timeslot timeslot;
    Practitioner practitioner;



    @PlanningVariable(valueRangeProviderRefs = {"practitionersRange"})
    public Practitioner getPractitioner() {
        return practitioner;
    }

    public void setPractitioner(Practitioner practitioner) {
        this.practitioner = practitioner;
    }

    @PlanningVariable(valueRangeProviderRefs = {"timeslotsRange"})
    public Timeslot getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(Timeslot timeslot) {
        this.timeslot = timeslot;
    }

    public Schedule(Timeslot timeslot, Practitioner practitioner) {
        this.timeslot = timeslot;
        this.practitioner = practitioner;
    }

    public Schedule() {
    }

}
