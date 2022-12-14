package com.softwarehouse.serviceorder.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee")
@EqualsAndHashCode(callSuper = true)
public class Employee extends Person {
    private String cv;

    @OneToOne
    @JoinColumn(name = "position_salary_id")
    private PositionSalary positionSalary;

    @JoinColumn(name = "employee_id")
    @OneToMany(cascade = CascadeType.ALL)
    private List<Address> addresses;

}
