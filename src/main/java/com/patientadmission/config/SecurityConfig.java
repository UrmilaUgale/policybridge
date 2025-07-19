package com.patientadmission.config;

import com.patientadmission.service.AdminService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // No longer needed here
import org.springframework.security.crypto.password.PasswordEncoder; // Still needed for method signature
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AdminService adminService;
    // No longer injecting PasswordEncoder directly into SecurityConfig's constructor
    // as it will be provided by the Application class.

    public SecurityConfig(AdminService adminService) {
        this.adminService = adminService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/admitPatient", "/searchPatient", "/downloadFile/**",
                                "/css/**", "/js/**", "/images/**", "/index.html", "/search.html",
                                "/admin/login", "/insurance.html", "/updateInsuranceComment").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/admin/login")
                        .loginProcessingUrl("/admin/login")
                        .defaultSuccessUrl("/admin/dashboard", true)
                        .failureUrl("/admin/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/admin/logout"))
                        .logoutSuccessUrl("/admin/login?logout=true")
                        .permitAll()
                );
        return http.build();
    }

    // REMOVE THIS BEAN DEFINITION FROM HERE:
    // @Bean
    // public PasswordEncoder passwordEncoder() {
    //     return new BCryptPasswordEncoder();
    // }

    @Bean
    public UserDetailsService userDetailsService() {
        return adminService;
    }

    // This bean now needs PasswordEncoder as a parameter, which Spring will inject from Application.java
    @Bean
    public DaoAuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder) { // Add PasswordEncoder parameter
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder); // Use the injected passwordEncoder
        return authProvider;
    }
}
