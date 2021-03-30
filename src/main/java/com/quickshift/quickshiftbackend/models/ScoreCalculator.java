package com.quickshift.quickshiftbackend.models;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.calculator.EasyScoreCalculator;


public class ScoreCalculator implements EasyScoreCalculator<ScheduleTable, HardSoftScore> {

    @Override
    public HardSoftScore calculateScore(ScheduleTable scheduleTable) {
        int hardScore = 0;
        int softScore = 0;


        return HardSoftScore.of(hardScore, softScore);
    }
}
