package com.mike.demorestfulservice.repository;

import com.mike.demorestfulservice.entity.Token;
import com.mike.demorestfulservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, Long> {

    List<Token> findAllByUser(User user);

    Token findTokenByToken(String token);
}
