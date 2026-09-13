package com.example.taskmanagement.service;

import com.example.taskmanagement.dto.PageResponse;
import com.example.taskmanagement.dto.TaskRequest;
import com.example.taskmanagement.dto.TaskResponse;
import com.example.taskmanagement.entity.Task;
import com.example.taskmanagement.entity.TaskPriority;
import com.example.taskmanagement.entity.TaskStatus;
import com.example.taskmanagement.entity.User;
import com.example.taskmanagement.exception.TaskNotFoundException;
import com.example.taskmanagement.exception.UserNotFoundException;
import com.example.taskmanagement.repository.TaskRepository;
import com.example.taskmanagement.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of(
            "id",
            "title",
            "status",
            "priority",
            "deadline"
    );

    public TaskServiceImpl(
            TaskRepository taskRepository,
            UserRepository userRepository
    ) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TaskResponse createTask(TaskRequest taskRequest) {

        User currentUser = getCurrentUser();

        Task task = new Task();

        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setStatus(taskRequest.getStatus());
        task.setPriority(taskRequest.getPriority());
        task.setDeadline(taskRequest.getDeadline());

        task.setUser(currentUser);

        if (taskRequest.getAssignedUserId() != null) {

            User assignedUser = userRepository
                    .findById(taskRequest.getAssignedUserId())
                    .orElseThrow(() ->
                            new UserNotFoundException(
                                    "Assigned user not found with id: "
                                            + taskRequest.getAssignedUserId()
                            )
                    );

            task.setAssignedUser(assignedUser);
        }

        Task savedTask = taskRepository.save(task);

        return convertToTaskResponse(savedTask);
    }

    @Override
    public List<TaskResponse> getAllTasks() {

        User currentUser = getCurrentUser();

        return taskRepository.findByUser(currentUser)
                .stream()
                .map(this::convertToTaskResponse)
                .toList();
    }

    @Override
    public PageResponse<TaskResponse> getAllTasks(
            int page,
            int size,
            String sortBy,
            String direction
    ) {

        User currentUser = getCurrentUser();

        // Validate sort field
        if (sortBy == null || !ALLOWED_SORT_FIELDS.contains(sortBy)) {
            throw new IllegalArgumentException(
                    "Invalid sort field. Allowed fields are: "
                            + ALLOWED_SORT_FIELDS
            );
        }

        // Validate direction
        if (direction == null ||
                (!direction.equalsIgnoreCase("asc")
                        && !direction.equalsIgnoreCase("desc"))) {

            throw new IllegalArgumentException(
                    "Invalid sort direction. Use 'asc' or 'desc'."
            );
        }

        Sort.Direction sortDirection =
                direction.equalsIgnoreCase("desc")
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortDirection, sortBy)
        );

        Page<Task> taskPage =
                taskRepository.findAllAccessibleTasks(
                        currentUser,
                        pageable
                );

        List<TaskResponse> tasks = taskPage.getContent()
                .stream()
                .map(this::convertToTaskResponse)
                .toList();

        return PageResponse.<TaskResponse>builder()
                .content(tasks)
                .page(taskPage.getNumber())
                .size(taskPage.getSize())
                .totalElements(taskPage.getTotalElements())
                .totalPages(taskPage.getTotalPages())
                .last(taskPage.isLast())
                .build();
    }

    @Override
    public TaskResponse getTaskById(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException(
                                "Task not found with id: " + id
                        ));

        checkTaskAccess(task);

        return convertToTaskResponse(task);
    }

    @Override
    public TaskResponse updateTask(
            Long id,
            TaskRequest taskRequest
    ) {

        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException(
                                "Task not found with id: " + id
                        ));

        checkTaskOwnership(existingTask);

        existingTask.setTitle(taskRequest.getTitle());
        existingTask.setDescription(taskRequest.getDescription());
        existingTask.setStatus(taskRequest.getStatus());
        existingTask.setPriority(taskRequest.getPriority());
        existingTask.setDeadline(taskRequest.getDeadline());

        if (taskRequest.getAssignedUserId() != null) {

            User assignedUser = userRepository
                    .findById(taskRequest.getAssignedUserId())
                    .orElseThrow(() ->
                            new UserNotFoundException(
                                    "Assigned user not found with id: "
                                            + taskRequest.getAssignedUserId()
                            )
                    );

            existingTask.setAssignedUser(assignedUser);
        }

        Task updatedTask = taskRepository.save(existingTask);

        return convertToTaskResponse(updatedTask);
    }

    @Override
    public TaskResponse updateTaskStatus(
            Long id,
            TaskStatus status
    ) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException(
                                "Task not found with id: " + id
                        ));

        checkTaskAccess(task);

        task.setStatus(status);

        Task updatedTask = taskRepository.save(task);

        return convertToTaskResponse(updatedTask);
    }

    @Override
    public void deleteTask(Long id) {

        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException(
                                "Task not found with id: " + id
                        ));

        checkTaskOwnership(existingTask);

        taskRepository.delete(existingTask);
    }

    @Override
    public List<TaskResponse> searchAndFilterTasks(
            TaskStatus status,
            TaskPriority priority,
            Long assignedUserId,
            String search
    ) {

        User currentUser = getCurrentUser();

        if (search != null && search.trim().isEmpty()) {
            search = null;
        }

        List<Task> tasks = taskRepository.searchAndFilterTasks(
                currentUser,
                status,
                priority,
                assignedUserId,
                search
        );

        return tasks.stream()
                .map(this::convertToTaskResponse)
                .toList();
    }

    private User getCurrentUser() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "Authenticated user not found"
                        )
                );
    }

    private void checkTaskAccess(Task task) {

        User currentUser = getCurrentUser();

        boolean isCreator =
                task.getUser() != null &&
                        task.getUser().getId().equals(currentUser.getId());

        boolean isAssignedUser =
                task.getAssignedUser() != null &&
                        task.getAssignedUser().getId().equals(currentUser.getId());

        if (!isCreator && !isAssignedUser) {

            throw new AccessDeniedException(
                    "You are not allowed to access this task"
            );
        }
    }

    private void checkTaskOwnership(Task task) {

        User currentUser = getCurrentUser();

        if (task.getUser() == null ||
                !task.getUser().getId().equals(currentUser.getId())) {

            throw new AccessDeniedException(
                    "You are not allowed to modify this task"
            );
        }
    }

    private TaskResponse convertToTaskResponse(Task task) {

        TaskResponse.TaskResponseBuilder response =
                TaskResponse.builder()
                        .id(task.getId())
                        .title(task.getTitle())
                        .description(task.getDescription())
                        .status(task.getStatus())
                        .priority(task.getPriority())
                        .deadline(task.getDeadline());

        if (task.getUser() != null) {

            response.userId(task.getUser().getId())
                    .userName(task.getUser().getName())
                    .userEmail(task.getUser().getEmail());
        }

        if (task.getAssignedUser() != null) {

            response.assignedUserId(task.getAssignedUser().getId())
                    .assignedUserName(task.getAssignedUser().getName())
                    .assignedUserEmail(task.getAssignedUser().getEmail());
        }

        return response.build();
    }
}