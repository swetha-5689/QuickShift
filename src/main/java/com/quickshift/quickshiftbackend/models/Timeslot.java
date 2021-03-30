package com.quickshift.quickshiftbackend.models;


//Each slot for working

import org.optaplanner.core.api.domain.lookup.PlanningId;

public class Timeslot {
    String start;
    String end;
    @PlanningId
    String slotId;

    public Timeslot() {
    }

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
