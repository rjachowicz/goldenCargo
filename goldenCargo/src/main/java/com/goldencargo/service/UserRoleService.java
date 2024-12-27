package com.goldencargo.service;

import com.goldencargo.model.entities.User;
import com.goldencargo.repository.UserRoleRepository;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public Long getCurrentRoleId(User user) {
        return userRoleRepository.findByUser(user)
                .map(userRole -> userRole.getRole().getRoleId())
                .orElse(null);
    }


}
