package com.planner.service;

import com.planner.dto.TaskResponseDTO;
import com.planner.exceptions.AccessDeniedException;
import com.planner.exceptions.TaskNotFoundException;
import com.planner.exceptions.UserNotFoundException;
import com.planner.model.Task;
import com.planner.dto.TaskRequestDTO;
import com.planner.model.User;
import com.planner.repository.TaskRepository;
import com.planner.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<TaskResponseDTO> findAll() {
        String email = (String) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
        return taskRepository.findAllByUser(user)
                .stream()
                .map(task -> new TaskResponseDTO(
                        task.getId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getDeadline(),
                        task.getPriority(),
                        task.getCompleted(),
                        task.getUser().getId()
                ))
                .toList();
    }

    public TaskResponseDTO findById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found."));
        checkAccess(task);

        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDeadline(),
                task.getPriority(),
                task.getCompleted(),
                task.getUser().getId()
        );
    }

    public TaskResponseDTO create(TaskRequestDTO request) {
        String email = (String) SecurityContextHolder.getContext().
                getAuthentication()
                .getPrincipal();

        Task task  = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .deadline(request.getDeadline())
                .priority(request.getPriority())
                .completed(false)
                .user(userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found")))
                .build();
        Task saved = taskRepository.save(task);

        return new TaskResponseDTO(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getDeadline(),
                saved.getPriority(),
                saved.getCompleted(),
                saved.getUser().getId()
        );
    }

    public TaskResponseDTO update(Long id, TaskRequestDTO request) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found."));
        checkAccess(task);

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDeadline(request.getDeadline());
        task.setPriority(request.getPriority());

        Task saved = taskRepository.save(task);
        return new TaskResponseDTO(saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getDeadline(),
                saved.getPriority(),
                saved.getCompleted(),
                saved.getUser().getId());
    }

    public void delete(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found."));
        checkAccess(task);
        taskRepository.deleteById(id);
    }

    private void checkAccess(Task task) {
        String email = (String) SecurityContextHolder.getContext().
                getAuthentication()
                .getPrincipal();
        if (!email.equals(task.getUser().getEmail())) {
            throw new AccessDeniedException("Access denied.");
        }
    }
}