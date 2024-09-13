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
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(length = 20)
    private String firstName;
    @Column(length = 20)
    private String lastName;
    @Column(length = 100, unique = true)
    private String email;
    @Column(length = 60)
    private String password;

    @OneToMany(mappedBy = "author")
    private List<Task> authorList;

    @OneToMany(mappedBy = "executor")
    private List<Task> executorList;

    @OneToMany(mappedBy = "user")
    private List<Token> tokenList;
}
