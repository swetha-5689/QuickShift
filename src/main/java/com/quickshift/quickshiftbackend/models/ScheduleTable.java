package com.quickshift.quickshiftbackend.models;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.SolverStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@PlanningSolution
public class ScheduleTable {
    @PlanningEntityCollectionProperty
    List<Schedule> schedules;

    @ValueRangeProvider(id = "practitionersRange")
    @ProblemFactCollectionProperty
    List<Practitioner> practitioners;

    @ValueRangeProvider(id = "timeslotsRange")
    @ProblemFactCollectionProperty
    List<Timeslot> timeslots;

    @ProblemFactCollectionProperty
    List<RequestOff> requestOffs;

    public HardMediumSoftScore getHardMediumSoftScore() {
        return hardMediumSoftScore;
    }

    public List<RequestOff> getRequestOffs() {
        return requestOffs;
    }

    public void setRequestOffs(List<RequestOff> requestOffs) {
        this.requestOffs = requestOffs;
    }

    public ScheduleTable(List<Schedule> schedules, List<Practitioner> practitioners, List<Timeslot> timeslots, List<RequestOff> requestOffs, HardSoftScore hardSoftScore, SolverStatus solverStatus) {
        this.schedules = schedules;
        this.practitioners = practitioners;
        this.timeslots = timeslots;
        this.requestOffs = requestOffs;
        this.hardMediumSoftScore = hardMediumSoftScore;
        this.solverStatus = solverStatus;
    }

    public void setHardSoftScore(HardMediumSoftScore hardMediumSoftScore) {
        this.hardMediumSoftScore = hardMediumSoftScore;
    }

    @PlanningScore
    private HardMediumSoftScore hardMediumSoftScore;

    private SolverStatus solverStatus;

    public ScheduleTable() {
        this.schedules = new ArrayList<>();
        this.practitioners = new ArrayList<>();
        this.timeslots = new ArrayList<>();
        this.requestOffs = new ArrayList<>();
    }

    public List<Practitioner> getPractitioners() {
        return practitioners;
    }

    public void setPractitioners(List<Practitioner> practitioners) {
        this.practitioners = practitioners;
    }

    public List<Timeslot> getTimeslots() {
        return timeslots;
    }

    public void setTimeslots(List<Timeslot> timeslots) {
        this.timeslots = timeslots;
    }

    public SolverStatus getSolverStatus() {
        return solverStatus;
    }

    public void setSolverStatus(SolverStatus solverStatus) {
        this.solverStatus = solverStatus;
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
