package com.goldencargo.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clients")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "contact_person", length = 100)
    private String contactPerson;

    @Pattern(regexp = "^\\+?[0-9\\s\\-()]{7,20}$")
    @Column(name = "phone", length = 20)
    private String phone;

    @Email(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Column(name = "nip", length = 20, nullable = false)
    private String nip;
}
