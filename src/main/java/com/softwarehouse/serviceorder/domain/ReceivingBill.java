package com.softwarehouse.serviceorder.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "receiving_bills")
public class ReceivingBill extends Billing {

}
