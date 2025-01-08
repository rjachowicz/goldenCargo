package com.goldencargo.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "goods")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Goods extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_id")
    private Long goodsId;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "weight", nullable = false)
    private Double weight;

    @Column(name = "dimensions", length = 100)
    private String dimensions;

    @Column(name = "special_handling_instructions", nullable = false)
    private String specialHandlingInstructions;

    @ManyToMany(mappedBy = "goods")
    private Set<ClientOrder> clientOrders = new HashSet<>();
}
