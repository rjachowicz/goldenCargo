package com.goldencargo.service;

import com.goldencargo.model.entities.Role;
import com.goldencargo.model.entities.User;
import com.goldencargo.model.entities.UserRole;
import com.goldencargo.repository.RoleRepository;
import com.goldencargo.repository.UserRepository;
import com.goldencargo.repository.UserRoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findByIsDeletedFalse();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> updateUser(Long id, User userDetails, String newPassword) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            user.setFirstName(userDetails.getFirstName());
            user.setLastName(userDetails.getLastName());
            user.setPhoneNumber(userDetails.getPhoneNumber());
            user.setAddress(userDetails.getAddress());
            user.setStatus(userDetails.getStatus());
            user.setUpdatedAt(new java.util.Date());
            if (newPassword != null && !newPassword.isEmpty()) {
                user.setPassword(passwordEncoder.encode(newPassword));
            }
            return userRepository.save(user);
        });
    }

    public List<User> getUsersNotAssignedAsDrivers() {
        return userRepository.findUsersNotAssignedAsDrivers();
    }

    public List<User> getUsersNotAssignedAsLogistic() {
        return userRepository.findUsersNotAssignedAsLogistic();
    }

    @Transactional
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.softDelete(id);
            return true;
        }
        return false;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmailAndIsDeletedFalse(email);
    }

    public void updateUserPassword(User user, String rawPassword) {
        user.setPassword(passwordEncoder.encode(rawPassword));
        userRepository.save(user);
    }

    public void assignRoleToUser(User user, Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        UserRole userRole = new UserRole(user, role);
        userRoleRepository.save(userRole);
    }

    @Transactional
    public void updateUserRole(Long userId, Long roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRoleRepository.deleteByUser(user);
        assignRoleToUser(user, roleId);
    }

}