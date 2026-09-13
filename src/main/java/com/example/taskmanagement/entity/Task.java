package com.example.taskmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @FutureOrPresent(
            message = "Deadline must be in the present or future"
    )
    private LocalDateTime deadline;

    // User who created the task
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // User assigned to complete the task
    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private User assignedUser;


}
