package com.quickshift.quickshiftbackend.service;


import com.quickshift.quickshiftbackend.models.*;
import com.quickshift.quickshiftbackend.solver.ScheduleTableConstraintProvider;

import org.optaplanner.core.api.score.ScoreExplanation;
import org.optaplanner.core.api.score.ScoreManager;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.api.solver.SolverManager;
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

    @PostMapping(path = "/")
    public ResponseEntity<ScoreExplanation<ScheduleTable, HardMediumSoftScore>> solve(@RequestBody RequestModel request) {
        ScheduleTable unsolvedScheduleTable = new ScheduleTable();
        List<Schedule> scheduleList = new ArrayList<>();
        for (int i = 0; i < request.getTimeslots().size(); i++) {
            unsolvedScheduleTable.getSchedules().add(new Schedule());
        }
        System.out.print(scheduleList);
        SolverFactory<ScheduleTable> solverFactory = SolverFactory.create(new SolverConfig()
            .withSolutionClass(ScheduleTable.class).withEntityClasses(Schedule.class)
                .withConstraintProviderClass(ScheduleTableConstraintProvider.class)
                .withTerminationSpentLimit(Duration.ofSeconds(20))
        );
        Solver<ScheduleTable> solver = solverFactory.buildSolver();
        unsolvedScheduleTable.getPractitioners().addAll(request.getPractitioners());
        unsolvedScheduleTable.getTimeslots().addAll(request.getTimeslots());
        unsolvedScheduleTable.getRequestOffs().addAll(request.getRequestOffs());
        ScheduleTable solvedScheduleTable = solver.solve(unsolvedScheduleTable);
        ScoreManager<ScheduleTable, HardMediumSoftScore> scoreManager = ScoreManager.create(solverFactory);
        ScoreExplanation<ScheduleTable, HardMediumSoftScore> scoreExplanation = scoreManager.explainScore(solvedScheduleTable);
        return new ResponseEntity<>(scoreExplanation, HttpStatus.OK);
    }

}
