package com.quickshift.quickshiftbackend.models;

public class Timeslot {
    String start;
    String end;

    public Timeslot() {
    }

    public Timeslot(String start, String end) {
        this.start = start;
        this.end = end;
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
