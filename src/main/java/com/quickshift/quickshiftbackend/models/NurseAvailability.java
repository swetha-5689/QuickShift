package com.quickshift.quickshiftbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneId;

public class NurseAvailability {
    @NotNull
    private Nurse nurse;

    @NotNull
    private OffsetDateTime startDateTime;
    @NotNull
    private OffsetDateTime endDateTime;

    @NotNull
    private NurseAvailabilityState state;

    @AssertTrue
    @JsonIgnore
    public boolean isValid() {
        return getDuration().getSeconds() / (60 * 60) < 28;
    }

    @JsonIgnore
    public Duration getDuration() {
        return Duration.between(startDateTime, endDateTime);
    }

    public Nurse getNurse() {
        return nurse;
    }

    public void setNurse(Nurse nurse) {
        this.nurse = nurse;
    }

    public OffsetDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(OffsetDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public OffsetDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(OffsetDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public NurseAvailabilityState getState() {
        return state;
    }

    public void setState(NurseAvailabilityState state) {
        this.state = state;
    }

    public NurseAvailability() {
    }

    public NurseAvailability(@NotNull Nurse nurse, @NotNull OffsetDateTime startDateTime, @NotNull OffsetDateTime endDateTime, @NotNull NurseAvailabilityState state) {
        this.nurse = nurse;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.state = state;
    }

    @Override
    public String toString() {
        return "NurseAvailability{" +
                "nurse=" + nurse +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", state=" + state +
                '}';
    }
}
