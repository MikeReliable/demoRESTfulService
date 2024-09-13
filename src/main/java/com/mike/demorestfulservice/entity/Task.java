package com.mike.demorestfulservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;
    @Column(nullable = false)
    private String text;

    @Enumerated(EnumType.STRING)
    private Priority priority;
    @Enumerated(EnumType.STRING)
    private Status status;

    @JoinColumn(name = "author")
    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

    @JoinColumn(name = "executor")
    @ManyToOne(fetch = FetchType.LAZY)
    private User executor;

    @OneToMany(mappedBy = "task")
    private List<Comment> commentList;
}
