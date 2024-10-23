package com.angaar.login_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.angaar.login_service.models.Vault;

public interface VaultRepository extends JpaRepository<Vault, Long> {
    // Custom query methods can be added here, if needed
    Vault findBySecret(String secret);  // Example: finding a vault entry by its secret
   
}
