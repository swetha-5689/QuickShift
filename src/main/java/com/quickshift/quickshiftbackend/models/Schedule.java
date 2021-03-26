package com.quickshift.quickshiftbackend.models;

public class Schedule {
    String start;
    String end;
    String practitioner;

    public String getPractitioner() {
        return practitioner;
    }

    public void setPractitioner(String practitioner) {
        this.practitioner = practitioner;
    }

    public Schedule(String start, String end, String practitioner) {
        this.start = start;
        this.end = end;
        this.practitioner = practitioner;
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

    public Schedule() {
    }

}
