package com.quickshift.quickshiftbackend.models;

import org.optaplanner.core.api.domain.lookup.PlanningId;

public class Practitioner {
    @PlanningId
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Practitioner() {
    }

    public Practitioner(String id) {
        this.id = id;
    }
}
