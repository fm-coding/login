package com.example.flightbooking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection for APIs
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/users/save", "/api/v1/users/login").permitAll() // Public endpoints
                        .requestMatchers("/api/v1/users/delete/{username}", "/api/v1/users/delete-all").hasRole("ADMIN") // Admin-only endpoints
                        .anyRequest().authenticated() // All other requests require authentication
                )
                .httpBasic(httpBasic -> {}) // Use HTTP Basic authentication
                .formLogin(formLogin -> formLogin.disable()) // Disable form-based login
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)) // Handle unauthenticated access
                        .accessDeniedHandler(new AccessDeniedHandlerImpl()) // Handle access denied
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("adminpass"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/api/v1/users/save", "/api/v1/users/login");
    }
}
