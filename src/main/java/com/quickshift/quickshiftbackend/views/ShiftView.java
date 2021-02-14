package com.quickshift.quickshiftbackend.views;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class ShiftView {
    private Long rotationEmployeeId;
    @NotNull
    private Long spotId;
    @NotNull
    private List<Long> requiredSkillSetIdList;

    @NotNull
    private LocalDateTime startDateTime;
    @NotNull
    private LocalDateTime endDateTime;
}
