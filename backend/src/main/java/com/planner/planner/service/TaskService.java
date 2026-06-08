package com.planner.planner.service;

import com.planner.planner.model.Task;
import com.planner.planner.dto.TaskDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {
    private final List<Task> tasks = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    public List<Task> findAll() {
        return tasks;
    }

    public Optional<Task> findById(Long id) {
        return tasks.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst();
    }

    public Task create(TaskDTO dto) {
        Task task = Task.builder()
                .id(counter.incrementAndGet())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .deadline(dto.getDeadline())
                .priority(dto.getPriority())
                .completed(false)
                .build();
        tasks.add(task);
        return task;
    }

    public void delete(Long id) {
        tasks.removeIf(t -> t.getId().equals(id));
    }
}
