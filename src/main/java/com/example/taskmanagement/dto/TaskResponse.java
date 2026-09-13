package com.example.taskmanagement.dto;

import com.example.taskmanagement.entity.TaskPriority;
import com.example.taskmanagement.entity.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDateTime deadline;

    // User who created the task
    private Long userId;
    private String userName;
    private String userEmail;

    // User assigned to complete the task
    private Long assignedUserId;
    private String assignedUserName;
    private String assignedUserEmail;

}
