package com.softwarehouse.serviceorder.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "service_order")
public class ServiceOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    private String channelSale;

    @OneToOne
    @JoinColumn(name = "attendant_id")
    private Employee attendant;

    @OneToOne
    @JoinColumn(name = "expert_id")
    private Employee expert;

    @Enumerated(EnumType.STRING)
    @Column(name = "serviceStatus", nullable = false)
    private ServiceOrderStatus status;

    private LocalDate startDate;
    private LocalDate endDate;

    private BigDecimal discountAmount;
    private BigDecimal discountPercent;
    private BigDecimal total;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "service_order_id")
    private List<ServiceOrderEquipment> serviceOrderEquipments;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "service_order_id")
    private List<ServiceOrderProduct> serviceOrderProducts;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "service_order_id")
    private List<ServiceOrderService> serviceOrderServices;

    @OneToOne
    @JoinColumn(name = "cost_center_id")
    private CostCenter costCenter;

    @JoinColumn(name = "address_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Address deliveryAddress;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "service_order_id")
    private List<Payment> payments;

    @Column(name = "other_information")
    private String otherInformation;

    @OneToMany
    @JoinColumn(name = "service_order_id")
    private List<Attachment> attachments;
}
