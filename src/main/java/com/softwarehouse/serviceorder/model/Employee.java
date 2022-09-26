package com.softwarehouse.serviceorder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee")
public class Employee extends Person{

    private String cv;

    @OneToOne
    @JoinColumn(name = "position_and_salary_id")
    private PositionAndSalary positionAndSalary;

    @JoinColumn(name = "position_and_salary_id")
    @OneToMany(cascade = CascadeType.ALL)
    private List<Address> addresses;

}
