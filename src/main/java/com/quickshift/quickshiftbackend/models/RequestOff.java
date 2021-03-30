package com.quickshift.quickshiftbackend.models;
//For days off and holidays
public class RequestOff {
    String practitioner;
    int priority;
    String dateStart;
    String dateEnd;

    public RequestOff() {
    }

    public RequestOff(String practitioner, int priority, String dateStart, String dateEnd) {
        this.practitioner = practitioner;
        this.priority = priority;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public String getPractitioner() {
        return practitioner;
    }

    public void setPractitioner(String practitioner) {
        this.practitioner = practitioner;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

}
