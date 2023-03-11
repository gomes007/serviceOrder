package com.softwarehouse.serviceorder.contexts.order.entities;

import com.softwarehouse.serviceorder.contexts.accounts.entities.AccountsPlan;
import com.softwarehouse.serviceorder.contexts.address.entities.Address;
import com.softwarehouse.serviceorder.contexts.costcenter.entities.CostCenter;
import com.softwarehouse.serviceorder.contexts.customer.entities.Customer;
import com.softwarehouse.serviceorder.contexts.employee.entities.Employee;
import com.softwarehouse.serviceorder.contexts.order.models.OrderStatus;
import com.softwarehouse.serviceorder.contexts.shared.entities.Attachment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    private LocalDate startDate;
    private LocalDate endDate;

    private BigDecimal discountAmount;
    private BigDecimal discountPercent;
    private BigDecimal total;

    @JoinColumn(name = "order_id")
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderEquipment> orderEquipments;

    @JoinColumn(name = "order_id")
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts;

    @JoinColumn(name = "order_id")
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderService> orderServices;

    @OneToOne
    @JoinColumn(name = "cost_center_id")
    private CostCenter costCenter;

    @JoinColumn(name = "delivery_address_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Address deliveryAddress;

    @OneToOne
    @JoinColumn(name = "accounts_plan_id")
    private AccountsPlan accountsPlan;

    @JoinColumn(name = "order_id")
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderPayment> payments;

    @Column(name = "other_information")
    private String otherInformation;

    @OneToMany
    @JoinTable(
            name = "order_attachments",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "attachment_id")
    )
    private List<Attachment> attachments;
}
