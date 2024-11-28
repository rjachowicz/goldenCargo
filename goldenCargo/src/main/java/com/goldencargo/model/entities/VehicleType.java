package com.goldencargo.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vehicle_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleType extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_type_id")
    private Long vehicleTypeId;

    @Column(name = "name", unique = true, nullable = false, length = 50)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "maximum_load")
    private Double maximumLoad;

    @Column(name = "dimensions", length = 100)
    private String dimensions;

    @Column(name = "special_features")
    private String specialFeatures;

    @OneToMany(mappedBy = "vehicleType")
    private Set<Vehicle> vehicles = new HashSet<>();


}
