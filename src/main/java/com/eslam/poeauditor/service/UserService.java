package com.eslam.poeauditor.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.eslam.poeauditor.constant.Scope;
import com.eslam.poeauditor.constant.StashType;
import com.eslam.poeauditor.constant.UserRoleCode;
import com.eslam.poeauditor.domain.ItemOverview;
import com.eslam.poeauditor.domain.UserStashItem;
import com.eslam.poeauditor.domain.UserStashTab;
import com.eslam.poeauditor.domain.bundle.UserStashItemBundle;
import com.eslam.poeauditor.domain.bundle.UserStashTabBundle;
import com.eslam.poeauditor.exception.UserAlreadyExistsException;
import com.eslam.poeauditor.exception.UserNotFoundException;
import com.eslam.poeauditor.exception.UserRoleNotFoundException;
import com.eslam.poeauditor.model.AuthorizationGrant;
import com.eslam.poeauditor.model.PoeUserDetails;
import com.eslam.poeauditor.model.User;
import com.eslam.poeauditor.model.UserRole;
import com.eslam.poeauditor.repository.UserRepository;
import com.eslam.poeauditor.repository.UserRoleRepository;


@Service
@DependsOn()
public class UserService {

    private final Logger logger = LogManager.getLogger(getClass());
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private PoeApiService poeApiService;

    

    public User createUser(User user, UserRoleCode userRoleCode) throws UserAlreadyExistsException, UserRoleNotFoundException {
        Optional<User> theUser = userRepository.findByEmailId(user.getEmailId());
        if (theUser.isPresent()){
            throw new UserAlreadyExistsException("A user with " +user.getEmailId() +" already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Optional<UserRole> userRole = userRoleRepository.findByUserRoleCode(userRoleCode);

        if (!userRole.isPresent()) {
            throw new UserRoleNotFoundException(String.format("Could not find role with code: %s", userRoleCode));
        }

        user.addRole(userRole.get());
        return userRepository.save(user);
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public User getByEmailId(String emailId) {
        Optional<User> user = userRepository.findByEmailId(emailId);
        if (!user.isPresent()) {
            throw new UserNotFoundException("Specified email is not associated with a user.");
        }

        return user.get();
    }

    public User getFromUserDetails(PoeUserDetails poeUserDetails) {
        Optional<User> user = userRepository.findByEmailId(poeUserDetails.getUsername());
        if (!user.isPresent()) {
            throw new UserNotFoundException("Could not find a user with the specified user details");
        }
        return user.get();
    }

    public User getByUserRoleCode(UserRoleCode userRoleCode) {
        Optional<User> user = userRepository.findByUserRolesUserRoleCode(userRoleCode);
        if (!user.isPresent()) {
            throw new UserNotFoundException(
                String.format("Could not find a user with the specified user role code: %s", userRoleCode));
        }
        return user.get();
    }

    public User saveUser(User user) {
        return userRepository.save(user); 
    }

    public List<UserStashTab> getUserStashTabs(User user, String league) {
        Optional<AuthorizationGrant> authorizationGrant = user.getUserAuthorizationGrants().stream()
        .filter(grant -> grant.getScope().equals(Scope.STASHES)).max((grant1, grant2) -> 
        grant1.getExpiresAt().compareTo(grant2.getExpiresAt()));

        if (authorizationGrant.isEmpty()) {
            throw new IllegalAccessError("User does not have a stash token. Please reauthenticate with poe account");
        }
        if (authorizationGrant.get().getExpiresAt().before(Date.from(Instant.now()))) {
            throw new IllegalAccessError("User POE token expired. Please reauthenticate with poe account");
        }
        if (itemService.getLeagues().stream().noneMatch(l -> l.equals(league))) {
            throw new IllegalArgumentException("Provided league is invalid");
        }


        UserStashTabBundle userStashTabBundle = poeApiService.requestUserStashTabs(authorizationGrant.get(), league);

        if (userStashTabBundle == null || userStashTabBundle.getUserStashTabs() == null) {
            return new ArrayList<>();
        }
        return userStashTabBundle.getUserStashTabs(StashType.implementedStashes(), true);
    }

    public List<UserStashItem> getUserStashTabItems(User user, String league, String stashId) {
        Optional<AuthorizationGrant> authorizationGrant = user.getUserAuthorizationGrants().stream()
        .filter(grant -> grant.getScope().equals(Scope.STASHES)).max((grant1, grant2) -> 
        grant1.getExpiresAt().compareTo(grant2.getExpiresAt()));

        if (authorizationGrant.isEmpty()) {
            throw new IllegalAccessError("User does not have a stash token. Please reauthenticate with poe account");
        }
        if (authorizationGrant.get().getExpiresAt().before(Date.from(Instant.now()))) {
            throw new IllegalAccessError("User POE token expired. Please reauthenticate with poe account");
        }
        if (itemService.getLeagues().stream().noneMatch(l -> l.equals(league))) {
            throw new IllegalArgumentException("Provided league is invalid");
        }

        UserStashItemBundle userStashItemBundle = poeApiService.requestUserStashItems(authorizationGrant.get(), league, stashId);

        if (userStashItemBundle == null || userStashItemBundle.getItemStash().getUserStashItems() == null) {
            return new ArrayList<>();
        }
        List<ItemOverview> itemOverviews = itemService.getItemOverview(league);
        List<UserStashItem> userStashItems = new ArrayList<>();
        userStashItemBundle.getItemStash().getUserStashItems().parallelStream().forEachOrdered(item -> {
            Optional<ItemOverview> itemOverview = itemOverviews.parallelStream()
            .filter(overview -> overview.getName().equalsIgnoreCase(item.getTypeLine())).findAny();
            if (itemOverview.isPresent()) {
                UserStashItem userStashItem = item;
                userStashItem.setChaosValue(itemOverview.get().getChaosValue());
                
                Optional<UserStashItem> existingItem = userStashItems.parallelStream().filter(ei -> ei.getTypeLine().
                equals(userStashItem.getTypeLine())).findAny();

                if (existingItem.isPresent()) {
                    existingItem.get().setStackSize(existingItem.get().getStackSize() + userStashItem.getStackSize());
                }
                else {
                    userStashItems.add(userStashItem);
                }
            }
        });

        return userStashItems;
    }
}
