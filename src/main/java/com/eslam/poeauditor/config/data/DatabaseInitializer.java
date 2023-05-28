package com.eslam.poeauditor.config.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import com.eslam.poeauditor.assembler.ModelAssembler;
import com.eslam.poeauditor.constant.GrantType;
import com.eslam.poeauditor.constant.Scope;
import com.eslam.poeauditor.constant.UserRoleCode;
import com.eslam.poeauditor.domain.AuthorizationTokenDto;
import com.eslam.poeauditor.exception.DatasourceInitializationException;
import com.eslam.poeauditor.exception.UserAlreadyExistsException;
import com.eslam.poeauditor.exception.UserRoleNotFoundException;
import com.eslam.poeauditor.model.AuthorizationGrant;
import com.eslam.poeauditor.model.User;
import com.eslam.poeauditor.model.UserRole;
import com.eslam.poeauditor.repository.UserAuthorizationCodeRepository;
import com.eslam.poeauditor.repository.UserRepository;
import com.eslam.poeauditor.repository.UserRoleRepository;
import com.eslam.poeauditor.request.TokenRequest;
import com.eslam.poeauditor.service.SecurityService;
import com.eslam.poeauditor.service.UserService;

import jakarta.transaction.Transactional;

@Component
public class DatabaseInitializer implements ApplicationRunner{

    private final Logger logger = LogManager.getLogger(getClass());

    @Value("${datasource.admin.username}")
    private String adminUsername;

    @Value("${datasource.admin.password}")
    private String adminPassword;

    @Value("${poe.token.url}")
    private String poeTokenUrl;

    @Value("${client.id}")
    private String clientId;
    
    @Value("${client.secret}")
    private String clientSecret;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuthorizationCodeRepository userAuthorizationCodeRepository;

    

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws DatasourceInitializationException, UserAlreadyExistsException, UserRoleNotFoundException, RestClientException, IllegalArgumentException, IllegalAccessException {
        logger.info("Initializing default admin credentials.");
        
        Optional<UserRole> userRole = userRoleRepository.findByUserRoleCode(UserRoleCode.ADMIN);

        if (!userRole.isPresent()) {
            throw new DatasourceInitializationException("Failed to initialize Admin user. ADMIN role not found");
        }
        
        Optional<User> existingUser = userRepository.findByEmailId(adminUsername);
        User adminUser;
        if (!existingUser.isPresent()) {
            logger.info("Admin user not found. Adding entry to database.");
            User user = User.builder().emailId(adminUsername).password(adminPassword).build();
            adminUser = userService.createUser(user, UserRoleCode.SUPER_MAIN_ADMIN);
        } else {
            logger.info("Admin user exists in database.");
            adminUser = existingUser.get();
        }

        List<AuthorizationGrant> serviceAuthorizationGrants = userService.getByEmailId(adminUsername).getUserAuthorizationGrants();
        if (serviceAuthorizationGrants == null) {
            serviceAuthorizationGrants = new ArrayList<>();
        }

        for (Scope service : Scope.availableServices()) {
            if (serviceAuthorizationGrants.stream().noneMatch(s -> s.getScope().equals(service))) {
                TokenRequest tokenRequest = TokenRequest.builder().clientId(clientId).clientSecret(clientSecret)
                .grantType(GrantType.CLIENT_CREDENTIALS).scope(service).build();
                logger.info("Requesting grant for service: {}", service.getStringValue());
                AuthorizationTokenDto authorizationTokenDto = securityService.getAuthorizationToken(tokenRequest);

                userAuthorizationCodeRepository.save(ModelAssembler.assemble(authorizationTokenDto, adminUser));
            }
        }

    }
}
