package com.planner.planner.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class Task {
    private Long id;
    private String title;
    private String description;
    private LocalDate deadline;
    private Integer priority;
    private Boolean completed;
}
