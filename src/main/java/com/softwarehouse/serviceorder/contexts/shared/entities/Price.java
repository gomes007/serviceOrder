package com.softwarehouse.serviceorder.contexts.shared.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Price {
    private BigDecimal unitCost = BigDecimal.ZERO;
    private BigDecimal additionalCost = BigDecimal.ZERO;
    private BigDecimal finalCost = BigDecimal.ZERO;
    private BigDecimal profitPercent = BigDecimal.ZERO;
    private BigDecimal salePrice = BigDecimal.ZERO;
    private BigDecimal suggestedProfitPercent = BigDecimal.ZERO;
    private BigDecimal usedProfitPercent = BigDecimal.ZERO;
    private BigDecimal suggestedSaleValue = BigDecimal.ZERO;
    private BigDecimal usedSaleValue = BigDecimal.ZERO;
}

