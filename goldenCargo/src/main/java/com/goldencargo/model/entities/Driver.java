package com.goldencargo.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "driver")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userId;
    @Column(name = "driving_license", nullable = false)
    private String drivingLicense;
    @Column(name = "date_of_employment", nullable = false)
    private Long dateOfEmployment;
    @Column(name = "status", nullable = false)
    private Integer status;

    public Driver() {
    }

    public Driver(Long id, User userId, String drivingLicense, Long dateOfEmployment, Integer status) {
        this.id = id;
        this.userId = userId;
        this.drivingLicense = drivingLicense;
        this.dateOfEmployment = dateOfEmployment;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(String drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public Long getDateOfEmployment() {
        return dateOfEmployment;
    }

    public void setDateOfEmployment(Long dateOfEmployment) {
        this.dateOfEmployment = dateOfEmployment;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}