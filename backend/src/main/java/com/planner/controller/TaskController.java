package com.planner.controller;

import com.planner.dto.TaskResponseDTO;
import com.planner.dto.TaskRequestDTO;
import com.planner.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public List<TaskResponseDTO> findAll() {
        return taskService.findAll();
    }

    @GetMapping("/tasks/{id}")
    public TaskResponseDTO findById(@PathVariable Long id) {
        return taskService.findById(id);
    }

    @PostMapping("/tasks")
    public TaskResponseDTO create(@RequestBody TaskRequestDTO request) {
        return taskService.create(request);
    }

    @PutMapping("/tasks/{id}")
    public TaskResponseDTO update(@PathVariable Long id, @RequestBody TaskRequestDTO request) {
        return taskService.update(id, request);
    }

    @DeleteMapping("/tasks/{id}")
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }
}
