package com.softwarehouse.serviceorder.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int minQuantity;
    private int maxQuantity;
    private int currentQuantity;
}
