// MultipleFiles/Application.java
package com.patientadmission;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean; // Import this
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Import this
import org.springframework.security.crypto.password.PasswordEncoder; // Import this

@SpringBootApplication
@ComponentScan(basePackages = {"com.patientadmission", "com.patientadmission.config"})
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  // Define the PasswordEncoder bean here
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
