package com.softwarehouse.serviceorder.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    private String clientName;
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

    @JoinColumn(name = "service_order_id")
    @OneToMany(cascade = CascadeType.ALL)
    private List<ServiceOrderEquipment> serviceOrderEquipments;

    @JoinColumn(name = "service_order_id")
    @OneToMany(cascade = CascadeType.ALL)
    private List<ServiceOrderProduct> serviceOrderProducts;

    @JoinColumn(name = "service_order_id")
    @OneToMany(cascade = CascadeType.ALL)
    private List<ServiceOrderService> serviceOrderServices;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "service_order_cost_center", joinColumns = @JoinColumn(name = "service_order_id"), inverseJoinColumns = @JoinColumn(name = "cost_center_id"))
    private CostCenter costCenter;

    @JoinColumn(name = "address_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Address deliveryAddress;

    @JoinColumn(name = "service_order_id")
    @OneToMany(cascade = CascadeType.ALL)
    private List<Payment> payments;

    @Column(name = "other_information")
    private String otherInformation;

    @JoinColumn(name = "service_order_id")
    @OneToMany(cascade = CascadeType.ALL)
    private List<Attachment> attachments;
}
