package com.quickshift.quickshiftbackend.service;


import com.quickshift.quickshiftbackend.models.*;
import org.optaplanner.core.api.score.ScoreExplanation;
import org.optaplanner.core.api.score.ScoreManager;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.config.solver.SolverConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public String solve(@RequestBody RequestModel request) {
        ArrayList<Schedule> scheduleList = new ArrayList<>();
        for (int i = 0; i < request.getTimeslots().size(); i++) {
            scheduleList.add(new Schedule());
        }
        SolverFactory<ScheduleTable> solverFactory = SolverFactory.create(new SolverConfig()
            .withSolutionClass(ScheduleTable.class).withEntityClasses(Schedule.class).withEasyScoreCalculatorClass(ScoreCalculator.class)
        );
        ScoreManager<ScheduleTable, HardSoftScore> scoreManager = ScoreManager.create(solverFactory);
        Solver<ScheduleTable> solver = solverFactory.buildSolver();
        ScheduleTable solvedScheduleTable = solver.solve(unsolvedScheduleTable);
        return "Hello";
    }

}
