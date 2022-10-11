package com.softwarehouse.serviceorder.domain;

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
