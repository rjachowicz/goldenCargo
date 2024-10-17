package com.goldencargo.model.entities;

import com.goldencargo.model.data.UserRoleId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "user_roles")
@Getter
@Setter
@AllArgsConstructor
@IdClass(UserRoleId.class)
public class UserRole implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "assigned_at", nullable = false, updatable = false)
    private Date assignedAt;

    public UserRole() {
        this.assignedAt = new Date();
    }

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
        this.assignedAt = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRole that = (UserRole) o;
        return Objects.equals(user, that.user) && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, role);
    }
}
