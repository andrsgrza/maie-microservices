package com.angaar.login_service.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.angaar.login_service.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByTelephone(String telephone);
}