package com.softwarehouse.serviceorder.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Embeddable
@NoArgsConstructor
public class Price {
    private BigDecimal unitCost;
    private BigDecimal additionalCost;
    private BigDecimal finalCost;
    private BigDecimal profitPercent;
    private BigDecimal salePrice;
}

