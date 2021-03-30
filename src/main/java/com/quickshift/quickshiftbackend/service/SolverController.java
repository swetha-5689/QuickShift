package com.quickshift.quickshiftbackend.service;


import com.quickshift.quickshiftbackend.models.*;
import com.quickshift.quickshiftbackend.solver.ScheduleTableConstraintProvider;
import org.optaplanner.core.api.score.ScoreExplanation;
import org.optaplanner.core.api.score.ScoreManager;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.config.solver.SolverConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/solve")
@CrossOrigin
@Validated
public class SolverController {

    @Autowired
    ScheduleTable unsolvedScheduleTable;

    @PostMapping(path = "/")
    public ResponseEntity<ResponseModel> solve(@RequestBody RequestModel request) {
        List<Schedule> scheduleList = new ArrayList<>();
        for (int i = 0; i < request.getTimeslots().size(); i++) {
            unsolvedScheduleTable.getSchedules().add(new Schedule());
        }
        System.out.print(scheduleList);
        SolverFactory<ScheduleTable> solverFactory = SolverFactory.create(new SolverConfig()
            .withSolutionClass(ScheduleTable.class).withEntityClasses(Schedule.class)
                .withConstraintProviderClass(ScheduleTableConstraintProvider.class)
                .withTerminationSpentLimit(Duration.ofMinutes(1))
        );
        ScoreManager<ScheduleTable, HardSoftScore> scoreManager = ScoreManager.create(solverFactory);
        Solver<ScheduleTable> solver = solverFactory.buildSolver();
        unsolvedScheduleTable.getPractitioners().addAll(request.getPractitioners());
        unsolvedScheduleTable.getTimeslots().addAll(request.getTimeslots());
        ScheduleTable solvedScheduleTable = solver.solve(unsolvedScheduleTable);
        ResponseModel responseModel = new ResponseModel(solvedScheduleTable.getSchedules());
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

}