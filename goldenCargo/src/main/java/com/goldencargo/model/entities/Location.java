package com.goldencargo.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "locations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Location extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "city", length = 50)
    private String city;

    @Column(name = "state", length = 50)
    private String state;

    @Column(name = "country", length = 50)
    private String country;

    @Column(name = "postal_code", length = 20)
    private String postalCode;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @OneToMany(mappedBy = "startLocation")
    private Set<TransportOrder> startTransportOrders = new HashSet<>();

    @OneToMany(mappedBy = "endLocation")
    private Set<TransportOrder> endTransportOrders = new HashSet<>();

    @OneToMany(mappedBy = "startLocation")
    private Set<Route> startRoutes = new HashSet<>();

    @OneToMany(mappedBy = "endLocation")
    private Set<Route> endRoutes = new HashSet<>();
}
