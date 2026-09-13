package com.example.taskmanagement.repository;

import com.example.taskmanagement.entity.Task;
import com.example.taskmanagement.entity.TaskPriority;
import com.example.taskmanagement.entity.TaskStatus;
import com.example.taskmanagement.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUser(User user);

    List<Task> findByAssignedUser(User assignedUser);

    @Query("""
        SELECT t FROM Task t
        WHERE (t.user = :currentUser OR t.assignedUser = :currentUser)
        AND (:status IS NULL OR t.status = :status)
        AND (:priority IS NULL OR t.priority = :priority)
        AND (:assignedUserId IS NULL OR t.assignedUser.id = :assignedUserId)
        AND (
            :search IS NULL
            OR LOWER(t.title) LIKE LOWER(
                CONCAT('%', CAST(:search AS string), '%')
            )
        )
    """)
    List<Task> searchAndFilterTasks(
            @Param("currentUser") User currentUser,
            @Param("status") TaskStatus status,
            @Param("priority") TaskPriority priority,
            @Param("assignedUserId") Long assignedUserId,
            @Param("search") String search
    );

    @Query("""
        SELECT t FROM Task t
        WHERE t.user = :currentUser
        OR t.assignedUser = :currentUser
    """)
    Page<Task> findAllAccessibleTasks(
            @Param("currentUser") User currentUser,
            Pageable pageable
    );
}