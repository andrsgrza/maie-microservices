package com.angaar.login_service.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.angaar.login_service.models.User;
import com.angaar.login_service.models.dto.BasicUserDTO;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByTelephone(String telephone);
    
    @Query("SELECT new com.angaar.login_service.models.dto.BasicUserDTO(u.id, u.username) FROM User u WHERE u.username LIKE %:query% ORDER BY u.username ASC")
    List<BasicUserDTO> findTop10ByUsernameContaining(@Param("query") String query);
}
