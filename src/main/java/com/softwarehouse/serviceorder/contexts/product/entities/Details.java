package com.softwarehouse.serviceorder.contexts.product.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Details {
    private boolean enabled;
    private boolean soldSeparately;
    private boolean enabledOnPDV;
}
