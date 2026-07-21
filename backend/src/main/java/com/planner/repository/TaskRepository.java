package com.planner.repository;

import com.planner.model.Task;
import com.planner.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByUser(User user);
}
