package com.finance.system.config;

import com.finance.system.entity.Role;
import com.finance.system.entity.User;
import com.finance.system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            log.info("Initializing default users...");
            
            // Create admin user
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            admin.setActive(true);
            userRepository.save(admin);
            log.info("Created admin user: username=admin, password=admin123");
            
            // Create viewer user
            User viewer = new User();
            viewer.setUsername("viewer");
            viewer.setPassword(passwordEncoder.encode("viewer123"));
            viewer.setRole(Role.VIEWER);
            viewer.setActive(true);
            userRepository.save(viewer);
            log.info("Created viewer user: username=viewer, password=viewer123");
            
            // Create analyst user
            User analyst = new User();
            analyst.setUsername("analyst");
            analyst.setPassword(passwordEncoder.encode("analyst123"));
            analyst.setRole(Role.ANALYST);
            analyst.setActive(true);
            userRepository.save(analyst);
            log.info("Created analyst user: username=analyst, password=analyst123");
            
            log.info("Default users initialized successfully!");
        } else {
            log.info("Users already exist, skipping initialization.");
        }
    }
}
