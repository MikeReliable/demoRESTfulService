package com.mike.demorestfulservice.repository;

import com.mike.demorestfulservice.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findAllByAuthor_UserId(Long authorId, Pageable pageable);

    Page<Task> findAllByExecutor_UserId(Long executorId, Pageable pageable);
}
