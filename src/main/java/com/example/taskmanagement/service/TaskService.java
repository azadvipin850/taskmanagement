package com.example.taskmanagement.service;

import com.example.taskmanagement.dto.PageResponse;
import com.example.taskmanagement.dto.TaskRequest;
import com.example.taskmanagement.dto.TaskResponse;
import com.example.taskmanagement.entity.TaskPriority;
import com.example.taskmanagement.entity.TaskStatus;

import java.util.List;

public interface TaskService {

    TaskResponse createTask(TaskRequest taskRequest);

    List<TaskResponse> getAllTasks();

    TaskResponse getTaskById(Long id);

    TaskResponse updateTask(Long id, TaskRequest taskRequest);

    TaskResponse updateTaskStatus(Long id, TaskStatus status);

    void deleteTask(Long id);

    List<TaskResponse> searchAndFilterTasks(
            TaskStatus status,
            TaskPriority priority,
            Long assignedUserId,
            String search
    );

    PageResponse<TaskResponse> getAllTasks(
            int page,
            int size,
            String sortBy,
            String direction
    );
}