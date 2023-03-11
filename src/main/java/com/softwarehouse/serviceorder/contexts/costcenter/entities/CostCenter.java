package com.softwarehouse.serviceorder.contexts.costcenter.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cost_centers")
public class CostCenter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "costCenterStatus", nullable = false)
    private CostCenterStatus status;
}
