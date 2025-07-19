// MultipleFiles/AdminService.java
package com.patientadmission.service;

import com.patientadmission.model.Admin;
import com.patientadmission.repository.AdminRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.User; // Import this
import org.springframework.security.core.userdetails.UserDetails; // Import this
import org.springframework.security.core.userdetails.UserDetailsService; // Import this
import org.springframework.security.core.userdetails.UsernameNotFoundException; // Import this
import org.springframework.security.crypto.password.PasswordEncoder; // Import this

import java.util.Optional;

@Service
@Transactional
public class AdminService implements UserDetailsService { // Implement UserDetailsService

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder; // Inject PasswordEncoder

    // Update constructor to receive PasswordEncoder
    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Admin> findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    public Admin saveAdmin(Admin admin) {
        // This method is called by AdminDataLoader, which already encodes the password.
        // If you call this method from other places, ensure the password is encoded before passing it.
        return adminRepository.save(admin);
    }

    // This authenticate method is no longer strictly needed if Spring Security handles authentication
    // via UserDetailsService, but keeping it for now.
    public boolean authenticate(String username, String rawPassword) {
        Optional<Admin> adminOptional = adminRepository.findByUsername(username);
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            // Compare raw password with stored encoded password
            return passwordEncoder.matches(rawPassword, admin.getPassword());
        }
        return false;
    }

    // Implement the loadUserByUsername method from UserDetailsService
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return adminRepository.findByUsername(username)
                .map(admin -> User.withUsername(admin.getUsername())
                        .password(admin.getPassword()) // This password is already BCrypt encoded
                        .roles("ADMIN") // Assign roles as needed (e.g., "ADMIN")
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Admin user not found: " + username));
    }
}
