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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Practitioner)) return false;
        else if(((Practitioner) obj).getId().equals(this.getId())) return true;
        return false;
    }
}
