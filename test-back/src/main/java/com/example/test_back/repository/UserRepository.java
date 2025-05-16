package com.example.test_back.repository;

import com.example.test_back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 해당 엔티티에 전달하는 email이 존재하는 경우 true, 그렇지 않을 경우 false 반환
    boolean existsByUsername(String username);

    // User 타입의 엔티티를 검색 (email 기준)
    // : 존재하면 해당 객체 반환, 그렇지 않을 경우 빈 Optional 객체 반환

    Optional<Object> findByUsername(String username);
    <T> Optional<T> findByRole(String role);
}