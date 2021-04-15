package com.quickshift.quickshiftbackend.solver;

import static org.optaplanner.core.api.score.stream.ConstraintCollectors.sumDuration;
import static org.optaplanner.core.api.score.stream.Joiners.*;

import com.quickshift.quickshiftbackend.models.Practitioner;
import com.quickshift.quickshiftbackend.models.RequestOff;
import com.quickshift.quickshiftbackend.models.Schedule;
import com.quickshift.quickshiftbackend.models.Timeslot;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;

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
                weeklyHoursUpperLimit(constraintFactory),
                practitionerConflict(constraintFactory),
                timeslotConflict(constraintFactory),
                assignEveryTimeslot(constraintFactory),
                dailyHoursLimit(constraintFactory),
                monthlyHoursLowerLimit(constraintFactory),
                noTwoConsecutiveShifts(constraintFactory),
                requestOffShiftsHigh(constraintFactory),
                requestOffShiftsLow(constraintFactory),
                requestOffShiftsMedium(constraintFactory)
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
                .filter((practitioner, day, time) -> time.toMinutes() > 720)
                .penalize("Practitioners cannot work more than 12 hours per day", HardMediumSoftScore.ONE_HARD);
    }

    Constraint assignEveryTimeslot(ConstraintFactory constraintFactory) {
        return constraintFactory.fromUnfiltered(Schedule.class)
                .filter((schedule -> schedule.getPractitioner() == null || schedule.getTimeslot() == null))
                .penalize("Assign practitioner to every timeslot", HardMediumSoftScore.ONE_HARD);
    }

    Constraint weeklyHoursUpperLimit(ConstraintFactory constraintFactory) {
        return constraintFactory.from(Practitioner.class)
                .join(Schedule.class, equal(Function.identity(), Schedule::getPractitioner))
                .groupBy((practitioner, schedule) -> practitioner,
                        ((practitioner, schedule) -> ZonedDateTime.parse(schedule.getTimeslot().getStart()).toLocalDate()
                                .with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))),
                        sumDuration((practitioner, schedule) -> Duration.between(ZonedDateTime.parse(schedule.getTimeslot().getStart()),
                                ZonedDateTime.parse(schedule.getTimeslot().getEnd()))))
                .filter((practitioner, day, time) -> time.toMinutes() > 2400)
                .penalize("Practitioners cannot work more than 40 hours per week", HardMediumSoftScore.ONE_HARD);
    }
    Constraint monthlyHoursLowerLimit(ConstraintFactory constraintFactory) {
        return constraintFactory.from(Practitioner.class)
                .join(Schedule.class, equal(Function.identity(), Schedule::getPractitioner))
                .groupBy((practitioner, schedule) -> practitioner,
                        ((practitioner, schedule) -> ZonedDateTime.parse(schedule.getTimeslot().getStart()).toLocalDate()
                                .with(TemporalAdjusters.firstDayOfMonth())),
                        sumDuration((practitioner, schedule) -> Duration.between(ZonedDateTime.parse(schedule.getTimeslot().getStart()),
                                ZonedDateTime.parse(schedule.getTimeslot().getEnd()))))
                .filter((practitioner, day, time) -> time.toHoursPart() <= 20)
                .penalize("Practitioners cannot work less than 20 hours per month", HardMediumSoftScore.ONE_SOFT);
    }

    Constraint noTwoConsecutiveShifts(ConstraintFactory constraintFactory) {
        return constraintFactory.fromUniquePair(Schedule.class)
                .filter((schedule, schedule2) -> (Duration.between(ZonedDateTime.parse(schedule.getTimeslot().getEnd()).toLocalDateTime(),
                        ZonedDateTime.parse(schedule2.getTimeslot().getStart()).toLocalDateTime()).toHoursPart() <= 10))
                .filter(((schedule, schedule2) -> schedule.getPractitioner().equals(schedule2.getPractitioner())))
                .penalize("Practitioners cannot work consecutive shifts", HardMediumSoftScore.ONE_HARD);
    }
    Constraint requestOffShiftsHigh(ConstraintFactory constraintFactory) {
        return constraintFactory.from(Schedule.class)
                .join(RequestOff.class, equal(Schedule::getPractitioner, RequestOff::getPractitioner))
                .groupBy(((schedule, requestOff) -> schedule.getTimeslot()),
                        ((schedule, requestOff) -> requestOff))
                .filter((timeslot, requestOff) -> ZonedDateTime.parse(timeslot.getStart()).toLocalDateTime()
                        .isAfter(ZonedDateTime.parse(requestOff.getDateStart()).toLocalDateTime()) &&
                        ZonedDateTime.parse(timeslot.getStart()).toLocalDateTime().isBefore(ZonedDateTime.parse(requestOff.getDateEnd()).toLocalDateTime()))
                .filter((timeslot, requestOff) -> requestOff.getPriority() == 1)
                .penalize("Shift requested off high priority", HardMediumSoftScore.ONE_HARD);
    }

    Constraint requestOffShiftsMedium(ConstraintFactory constraintFactory) {
        return constraintFactory.from(Schedule.class)
                .join(RequestOff.class, equal(Schedule::getPractitioner, RequestOff::getPractitioner))
                .groupBy(((schedule, requestOff) -> schedule.getTimeslot()),
                        ((schedule, requestOff) -> requestOff))
                .filter((timeslot, requestOff) -> ZonedDateTime.parse(timeslot.getStart()).toLocalDateTime()
                        .isAfter(ZonedDateTime.parse(requestOff.getDateStart()).toLocalDateTime()) &&
                        ZonedDateTime.parse(timeslot.getStart()).toLocalDateTime().isBefore(ZonedDateTime.parse(requestOff.getDateEnd()).toLocalDateTime()))
                .filter((timeslot, requestOff) -> requestOff.getPriority() == 2)
                .penalize("Shift requested off medium priority", HardMediumSoftScore.ONE_MEDIUM);
    }

    Constraint requestOffShiftsLow(ConstraintFactory constraintFactory) {
        return constraintFactory.from(Schedule.class)
                .join(RequestOff.class, equal(Schedule::getPractitioner, RequestOff::getPractitioner))
                .groupBy(((schedule, requestOff) -> schedule.getTimeslot()),
                        ((schedule, requestOff) -> requestOff))
                .filter((timeslot, requestOff) -> ZonedDateTime.parse(timeslot.getStart()).toLocalDateTime()
                        .isAfter(ZonedDateTime.parse(requestOff.getDateStart()).toLocalDateTime()) &&
                        ZonedDateTime.parse(timeslot.getStart()).toLocalDateTime().isBefore(ZonedDateTime.parse(requestOff.getDateEnd()).toLocalDateTime()))
                .filter((timeslot, requestOff) -> requestOff.getPriority() == 3)
                .penalize("Shift requested off low priority", HardMediumSoftScore.ONE_SOFT);
    }

}