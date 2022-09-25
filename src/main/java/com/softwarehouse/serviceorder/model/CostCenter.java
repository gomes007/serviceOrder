package com.softwarehouse.serviceorder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cost_center")
public class CostCenter{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "costCenterStatus", nullable = false)
    private CostCenterStatus status;


    @OneToOne(mappedBy = "costCenter")
    private ServiceOrder serviceOrder;

}
