package com.planner.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class TaskResponseDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDate deadline;
    private Integer priority;
    private Boolean completed;
    private Long userId;

    public TaskResponseDTO(Long id, String title, String description, LocalDate deadline, Integer priority, Long id1) {
    }
}
