package com.softwarehouse.serviceorder.contexts.employee.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "positions_and_salaries")
public class PositionSalary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String position;
    private BigDecimal salary;
    private BigDecimal commission;
    private String role;
}
