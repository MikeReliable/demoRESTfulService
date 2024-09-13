package com.mike.demorestfulservice.repository;

import com.mike.demorestfulservice.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
