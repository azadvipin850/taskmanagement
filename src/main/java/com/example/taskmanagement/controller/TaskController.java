package com.example.taskmanagement.controller;

import com.example.taskmanagement.dto.PageResponse;
import com.example.taskmanagement.dto.TaskRequest;
import com.example.taskmanagement.dto.TaskResponse;
import com.example.taskmanagement.entity.TaskPriority;
import com.example.taskmanagement.entity.TaskStatus;
import com.example.taskmanagement.service.TaskService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@SecurityRequirement(name = "bearerAuth")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @Valid @RequestBody TaskRequest taskRequest
    ) {
        return ResponseEntity.ok(
                taskService.createTask(taskRequest)
        );
    }

    // Get all tasks
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        return ResponseEntity.ok(
                taskService.getAllTasks()
        );
    }

    // Get paginated and sorted tasks
    @GetMapping("/page")
    public ResponseEntity<PageResponse<TaskResponse>> getAllTasksPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return ResponseEntity.ok(
                taskService.getAllTasks(
                        page,
                        size,
                        sortBy,
                        direction
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                taskService.getTaskById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest taskRequest
    ) {
        return ResponseEntity.ok(
                taskService.updateTask(id, taskRequest)
        );
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponse> updateTaskStatus(
            @PathVariable Long id,
            @RequestParam TaskStatus status
    ) {
        return ResponseEntity.ok(
                taskService.updateTaskStatus(id, status)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(
            @PathVariable Long id
    ) {
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task deleted successfully");
    }

    @GetMapping("/search")
    public ResponseEntity<List<TaskResponse>> searchAndFilterTasks(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) TaskPriority priority,
            @RequestParam(required = false) Long assignedUserId,
            @RequestParam(required = false) String search
    ) {
        return ResponseEntity.ok(
                taskService.searchAndFilterTasks(
                        status,
                        priority,
                        assignedUserId,
                        search
                )
        );
    }
}