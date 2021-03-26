package com.quickshift.quickshiftbackend.models;

import org.optaplanner.core.api.domain.solution.PlanningSolution;

import java.util.List;
@PlanningSolution
public class ScheduleTable {
    List<Schedule> schedules;

    public ScheduleTable() {
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public ScheduleTable(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}
