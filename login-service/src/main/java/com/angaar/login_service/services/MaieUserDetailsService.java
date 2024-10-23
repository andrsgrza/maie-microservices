package com.angaar.login_service.services;

import com.angaar.login_service.models.User;
import com.angaar.login_service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class MaieUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // Return the UserDetails with the password retrieved from Vaultg
        return new org.springframework.security.core.userdetails.User(
            user.get().getUsername(), 
            user.get().getPasswordVault().getSecret(),  // Get the password from Vault
            new ArrayList<>()
        );
    }
}