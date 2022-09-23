package com.softwarehouse.serviceorder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "serviceOrder")
public class ServiceOrder extends Service{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String clientName;
    private String channelSale;
    private String attendant;
    private String expert;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;

    private String equipmentName;
    private String brand;
    private String model;
    private String seriesNumber;
    private String condition;
    private String flaw;
    private String fittings;
    private String solution;
    private String technicalReport;
    private String warrantyTerms;

    private Double discount;
    private Double total;

    private Service service;


}
