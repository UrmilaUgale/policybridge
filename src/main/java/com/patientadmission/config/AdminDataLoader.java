// MultipleFiles/AdminDataLoader.java
package com.patientadmission.config;

import com.patientadmission.model.Admin;
import com.patientadmission.service.AdminService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder; // Import this

@Component
public class AdminDataLoader implements CommandLineRunner {

    private final AdminService adminService;
    private final PasswordEncoder passwordEncoder; // Inject PasswordEncoder

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    // Update constructor to receive PasswordEncoder
    public AdminDataLoader(AdminService adminService, PasswordEncoder passwordEncoder) {
        this.adminService = adminService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (adminService.findByUsername(adminUsername).isEmpty()) {
            // Encode the password before saving
            String encodedPassword = passwordEncoder.encode(adminPassword);
            Admin admin = new Admin(adminUsername, encodedPassword); // Save encoded password
            adminService.saveAdmin(admin);
            System.out.println("Default admin user created: " + adminUsername);
        }
    }
}
