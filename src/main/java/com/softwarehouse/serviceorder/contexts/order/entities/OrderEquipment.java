package com.softwarehouse.serviceorder.contexts.order.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_equipments")
public class OrderEquipment {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String brand;
    private String model;

    @Column(nullable = false, unique = true)
    private String seriesNumber;

    private String conditions;
    private String flaws;
    private String fittings;
    private String solution;
    private String technicalReport;
    private String warrantyTerms;
}
