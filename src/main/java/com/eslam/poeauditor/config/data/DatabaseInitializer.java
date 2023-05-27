package com.eslam.poeauditor.config.data;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.eslam.poeauditor.constant.UserRoleCode;
import com.eslam.poeauditor.exception.DatasourceInitializationException;
import com.eslam.poeauditor.exception.UserAlreadyExistsException;
import com.eslam.poeauditor.exception.UserRoleNotFoundException;
import com.eslam.poeauditor.model.User;
import com.eslam.poeauditor.model.UserRole;
import com.eslam.poeauditor.repository.UserRepository;
import com.eslam.poeauditor.repository.UserRoleRepository;
import com.eslam.poeauditor.service.UserService;

import jakarta.transaction.Transactional;

@Component
public class DatabaseInitializer implements ApplicationRunner{

    private final Logger logger = LogManager.getLogger(getClass());

    @Value("${datasource.admin.username}")
    private String adminUsername;

    @Value("${datasource.admin.password}")
    private String adminPassword;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws DatasourceInitializationException, UserAlreadyExistsException, UserRoleNotFoundException {
        logger.info("Initializing default admin credentials.");
        
        Optional<UserRole> userRole = userRoleRepository.findByUserRoleCode(UserRoleCode.ADMIN);

        if (!userRole.isPresent()) {
            throw new DatasourceInitializationException("Failed to initialize Admin user. ADMIN role not found");
        }
        
        Optional<User> existingUser = userRepository.findByEmailId(adminUsername);

        if (!existingUser.isPresent()) {
            logger.info("Admin user not found. Adding entry to database.");
            User user = User.builder().emailId(adminUsername).password(adminPassword).build();
            userService.createUser(user, UserRoleCode.ADMIN);
        } else {
            logger.info("Admin user exists in database.");
        }


    }
}
