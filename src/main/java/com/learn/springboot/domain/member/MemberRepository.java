package com.learn.springboot.domain.member;

import com.learn.springboot.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, String> {

    @Query("SELECT p FROM Member p ORDER BY p.name DESC")
    List<Member> findAllDesc();

    @Query("SELECT p FROM Member p where name = ?1 ORDER BY p.name DESC")
    Member findMemberByNameDesc(String name);
}
