package com.softwarehouse.serviceorder.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Embeddable
@NoArgsConstructor
public class Details {
    private boolean enabled;
    private boolean soldSeparately;
    private boolean enabledOnPDV;
}
