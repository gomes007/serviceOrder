package com.softwarehouse.serviceorder.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
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
    private BigDecimal suggestedProfitPercent;
    private BigDecimal usedProfitPercent;
    private BigDecimal suggestedSaleValue;
    private BigDecimal usedSaleValue;
}

