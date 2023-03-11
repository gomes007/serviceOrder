package com.softwarehouse.serviceorder.contexts.employee.entities;

import com.softwarehouse.serviceorder.contexts.address.entities.Address;
import com.softwarehouse.serviceorder.contexts.shared.entities.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employees")
@EqualsAndHashCode(callSuper = true)
public class Employee extends Person {
    private String cv;

    @OneToOne
    @JoinColumn(name = "position_salary_id")
    private PositionSalary positionSalary;

    @JoinColumn(name = "employee_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

}
