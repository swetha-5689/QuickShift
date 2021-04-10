package com.quickshift.quickshiftbackend.solver;

import static org.optaplanner.core.api.score.stream.ConstraintCollectors.sumDuration;
import com.quickshift.quickshiftbackend.models.Practitioner;
import com.quickshift.quickshiftbackend.models.Schedule;
import com.quickshift.quickshiftbackend.models.Timeslot;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import static org.optaplanner.core.api.score.stream.Joiners.equal;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;

import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjusters;
import java.time.Duration;
import java.util.function.Function;
import java.time.ZonedDateTime;

public class ScheduleTableConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[] {
                dailyHoursLimit(constraintFactory),
                practitionerConflict(constraintFactory),
                timeslotConflict(constraintFactory),
                assignEveryTimeslot(constraintFactory),
                weeklyHoursLimit(constraintFactory)
        };
    }

    Constraint practitionerConflict(ConstraintFactory constraintFactory) {
        return constraintFactory.fromUniquePair(Schedule.class, Joiners.equal(Schedule::getPractitioner),
                Joiners.equal(Schedule::getTimeslot)).penalize("Practitioner conflict", HardMediumSoftScore.ONE_HARD);
    }

    Constraint timeslotConflict(ConstraintFactory constraintFactory) {
        return constraintFactory.fromUniquePair(Schedule.class)
                .filter((schedule, schedule2) -> schedule.getTimeslot().getStart().equals(schedule2.getTimeslot().getStart()))
                .filter(((schedule, schedule2) -> schedule.getPractitioner().equals(schedule2.getPractitioner())))
                .penalize("Practitioners cannot work two slots at the same time", HardMediumSoftScore.ONE_HARD);
    }

    Constraint dailyHoursLimit(ConstraintFactory constraintFactory) {
        return constraintFactory.from(Practitioner.class)
                .join(Schedule.class, equal(Function.identity(), Schedule::getPractitioner))
                .groupBy((practitioner, schedule) -> practitioner,
                        ((practitioner, schedule) -> ZonedDateTime.parse(schedule.getTimeslot().getStart()).toLocalDate()),
                        sumDuration((practitioner, schedule) -> Duration.between(ZonedDateTime.parse(schedule.getTimeslot().getStart()),
                                ZonedDateTime.parse(schedule.getTimeslot().getEnd()))))
                .filter((practitioner, day, time) -> time.toHoursPart() > 12)
                .penalize("Practitioners cannot work more than 12 hours per day", HardMediumSoftScore.ONE_HARD);
    }

    Constraint assignEveryTimeslot(ConstraintFactory constraintFactory) {
        return constraintFactory.from(Timeslot.class)
                .ifNotExists(Schedule.class, Joiners.equal(Function.identity(), Schedule::getTimeslot))
                .penalize("Assign practitioner to every timeslot", HardMediumSoftScore.ONE_HARD);
    }

    Constraint weeklyHoursLimit(ConstraintFactory constraintFactory) {
        return constraintFactory.from(Practitioner.class)
                .join(Schedule.class, equal(Function.identity(), Schedule::getPractitioner))
                .groupBy((practitioner, schedule) -> practitioner,
                        ((practitioner, schedule) -> ZonedDateTime.parse(schedule.getTimeslot().getStart()).toLocalDate()
                                .with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))),
                        sumDuration((practitioner, schedule) -> Duration.between(ZonedDateTime.parse(schedule.getTimeslot().getStart()),
                                ZonedDateTime.parse(schedule.getTimeslot().getEnd()))))
                .filter((practitioner, day, time) -> time.toHoursPart() > 40)
                .penalize("Practitioners cannot work more than 40 hours per week", HardMediumSoftScore.ONE_HARD);
    }


}