package com.example.taskmanagement.dto;

import com.example.taskmanagement.entity.TaskPriority;
import com.example.taskmanagement.entity.TaskStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {


    @NotBlank(message = "Title is required")
    @Size(
            min = 3,
            max = 100,
            message = "Title must be between 3 and 100 characters"
    )
    private String title;

    @Size(
            max = 500,
            message = "Description cannot exceed 500 characters"
    )
    private String description;

    private TaskStatus status;

    private TaskPriority priority;

    @FutureOrPresent(message = "Deadline must be in the present or future")
    private LocalDateTime deadline;

    // ID of the user to whom this task is assigned
    private Long assignedUserId;


}
