package com.quickshift.quickshiftbackend.models;

import java.util.List;

public class ResponseModel {
    List<Schedule> schedules;

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public ResponseModel(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public ResponseModel() {

    }
}
