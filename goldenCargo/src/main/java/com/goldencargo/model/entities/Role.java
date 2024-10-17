package com.goldencargo.model.entities;

import com.goldencargo.model.data.UserRole;
import jakarta.persistence.*;

@Entity
@Table(name = "role", indexes = {
        @Index(name = "idx_role_id_role", columnList = "id, role")
})
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole role;

    public Role() {
    }

    public Role(Long id, UserRole role) {
        this.id = id;
        this.role = role;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
