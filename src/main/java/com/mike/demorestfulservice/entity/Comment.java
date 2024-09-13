package com.mike.demorestfulservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    @Column(nullable = false)
    private String text;

    @JoinColumn(name = "author")
    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

    @JoinColumn(name = "task")
    @ManyToOne(fetch = FetchType.LAZY)
    private Task task;
}
