package com.goldencargo.model.data;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class UserRoleId implements Serializable {

    private Long user;
    private Long role;

    public UserRoleId() {
    }

    public UserRoleId(Long user, Long role) {
        this.user = user;
        this.role = role;
    }

}
