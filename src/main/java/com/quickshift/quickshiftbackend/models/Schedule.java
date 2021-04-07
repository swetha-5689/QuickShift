package com.quickshift.quickshiftbackend.models;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import java.util.UUID;

@PlanningEntity
public class Schedule {
    Timeslot timeslot;
    Practitioner practitioner;
    @PlanningId
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Schedule(Timeslot timeslot, Practitioner practitioner, String id) {
        this.timeslot = timeslot;
        this.practitioner = practitioner;
        this.id = id;
    }

    public Schedule() {
        this.id = UUID.randomUUID().toString();
    }

}
