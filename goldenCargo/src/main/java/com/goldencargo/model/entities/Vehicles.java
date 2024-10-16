package com.goldencargo.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "vehicles", indexes = {
        @Index(name = "idx_vehicles_id_vin", columnList = "id, vin, production_year, registration_number")
})
public class Vehicles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "make", nullable = false)
    private String make;
    @Column(name = "model", nullable = false)
    private String model;
    @Column(name = "color")
    private String color;
    @Column(name = "engine", nullable = false)
    private String engine;
    @Column(name = "gear_type", nullable = false)
    private String gearType;
    @Column(name = "production_year", nullable = false)
    private Integer productionYear;
    @Column(name = "engine_displacement", nullable = false )
    private Double engineDisplacement;
    @Column(name = "status", nullable = false)
    private Integer status;
    @Column(name = "vin", nullable = false, unique = true)
    private String vin;
    @Column(name = "registration_number", unique = true, nullable = false)
    private String registrationNumber;

    public Vehicles() {
    }

    public Vehicles(Long id, String make, String model, String color, String engine, String gearType, Integer productionYear, Double engineDisplacement, Integer status, String vin, String registrationNumber) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.color = color;
        this.engine = engine;
        this.gearType = gearType;
        this.productionYear = productionYear;
        this.engineDisplacement = engineDisplacement;
        this.status = status;
        this.vin = vin;
        this.registrationNumber = registrationNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getGearType() {
        return gearType;
    }

    public void setGearType(String gearType) {
        this.gearType = gearType;
    }

    public Integer getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(Integer productionYear) {
        this.productionYear = productionYear;
    }

    public Double getEngineDisplacement() {
        return engineDisplacement;
    }

    public void setEngineDisplacement(Double engineDisplacement) {
        this.engineDisplacement = engineDisplacement;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
}
