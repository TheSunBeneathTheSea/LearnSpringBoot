package com.learn.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardsRepository extends JpaRepository<Boards, String>{

    @Query("SELECT p FROM Boards p ORDER BY p.name DESC")
    List<Boards> findAllDesc();

    @Query("SELECT p FROM Boards p where name = ?1 ORDER BY p.name DESC")
    List<Boards> findBoardDesc(String name);
}
