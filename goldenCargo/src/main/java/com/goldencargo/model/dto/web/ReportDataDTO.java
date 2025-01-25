package com.goldencargo.model.dto.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportDataDTO {

    private Integer transportId;
    private String actualDeparture;
    private String actualArrival;
    private String notes;
    private String transportCreatedAt;
    private String transportUpdatedAt;
    private String transportOrderName;
    private String scheduledDeparture;
    private String scheduledArrival;
    private String transportOrderCreatedAt;
    private String transportOrderUpdatedAt;
    private String licenseNumber;
    private String licenseCategory;
    private String hireDate;
    private String dateOfBirth;
    private String medicalCertificateExpiry;
    private String make;
    private String model;
    private String registrationNumber;
    private Double capacity;
    private Double mileage;
    private Integer vehicleYear;
    private String lastServiceDate;
    private String nextServiceDue;
    private String purchaseDate;
    private String vehicleTypeName;
    private Double maximumLoad;
    private String specialFeatures;
    private String dimensions;
    private String vehicleTypeDescription;
    private String startLocationName;
    private String startAddress;
    private String startCity;
    private String startState;
    private String startCountry;
    private String startPostalCode;
    private Double startLatitude;
    private Double startLongitude;
    private String endLocationName;
    private String endAddress;
    private String endCity;
    private String endState;
    private String endCountry;
    private String endPostalCode;
    private Double endLatitude;
    private Double endLongitude;
    private String driverName;
    private String driverPhone;
    private String driverEmail;
    private String clientOrderDate;
    private Integer clientOrderId;
    private String clientOrderPaymentStatus;
    private String clientOrderTotalAmount;
    private Integer clientId;
    private String clientName;
    private String clientNip;
    private String clientPhone;
    private String clientEmail;
    private String clientContactPerson;
    private String clientAddress;
}
