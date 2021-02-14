package com.quickshift.quickshiftbackend.models;

import com.quickshift.quickshiftbackend.views.ShiftView;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class Shift {
    private final AtomicLong lengthInMinutes = new AtomicLong(-1);

    private Nurse rotationNurse;

    @NotNull
    private Spot spot;
    @NotNull
    private Set<Skill> requiredSkillSet;

    @NotNull
    private OffsetDateTime startDateTime;

    @NotNull
    private OffsetDateTime endDateTime;

    private boolean pinnedByUser = false;

    private Nurse nurse = null;

    private Nurse originalNurse = null;

    @SuppressWarnings("unused")
    public Shift() {
    }



    @AssertTrue(message = "Shift's end date time is not at least 30 minutes" +
            " after shift's start date time")
    public boolean isValid() {
        return startDateTime != null && endDateTime != null &&
                (Duration.between(startDateTime, endDateTime).getSeconds() / 60) >= 30;
    }

    @Override
    public String toString() {
        return spot + " " + startDateTime + "-" + endDateTime;
    }

    public boolean precedes(Shift other) {
        return !endDateTime.isAfter(other.startDateTime);
    }

    public long getLengthInMinutes() { // Thread-safe cache.
        long currentLengthInMinutes = lengthInMinutes.get();
        if (currentLengthInMinutes >= 0) {
            return currentLengthInMinutes;
        }
        long newLengthInMinutes = startDateTime.until(endDateTime, ChronoUnit.MINUTES);
        lengthInMinutes.set(newLengthInMinutes);
        return newLengthInMinutes;
    }

    public boolean isMoved() {
        return originalNurse != null && originalNurse != nurse;
    }

    public boolean hasRequiredSkills() {
        return nurse.getSkillProficiencySet().containsAll(spot.getRequiredSkillSet()) &&
                nurse.getSkillProficiencySet().containsAll(requiredSkillSet);
    }


    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }

    public OffsetDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(OffsetDateTime startDateTime) {
        this.startDateTime = startDateTime;
        this.lengthInMinutes.set(-1);
    }

    public OffsetDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(OffsetDateTime endDateTime) {
        this.endDateTime = endDateTime;
        this.lengthInMinutes.set(-1);
    }

    public boolean isPinnedByUser() {
        return pinnedByUser;
    }

    public void setPinnedByUser(boolean lockedByUser) {
        this.pinnedByUser = lockedByUser;
    }

    public Nurse getNurse() {
        return nurse;
    }

    public Nurse getRotationNurse() {
        return rotationNurse;
    }

    public void setRotationNurse(Nurse rotationNurse) {
        this.rotationNurse = rotationNurse;
    }

    public void setNurse(Nurse nurse) {
        this.nurse = nurse;
    }

    public Nurse getOriginalNurse() {
        return originalNurse;
    }

    public void setOriginalNurse(Nurse originalNurse) {
        this.originalNurse = originalNurse;
    }

    public Set<Skill> getRequiredSkillSet() {
        return requiredSkillSet;
    }

    public void setRequiredSkillSet(Set<Skill> requiredSkillSet) {
        this.requiredSkillSet = requiredSkillSet;
    }

    /*public Shift inTimeZone(ZoneId zoneId) {
        Shift out = new Shift(zoneId, new ShiftView(), getSpot(), getRotationNurse(),
                getRequiredSkillSet(), getOriginalNurse());
        out.setNurse(getNurse());
        return out;
    }*/
}
