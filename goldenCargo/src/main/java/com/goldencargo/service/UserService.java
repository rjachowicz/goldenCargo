package com.goldencargo.service;

import com.goldencargo.model.entities.User;
import com.goldencargo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findByIsDeletedFalse();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
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
                user.setPassword(newPassword);
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
}