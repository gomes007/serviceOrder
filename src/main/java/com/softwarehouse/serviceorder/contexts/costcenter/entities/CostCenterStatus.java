package com.softwarehouse.serviceorder.contexts.costcenter.entities;

public enum CostCenterStatus {
    ENABLED("Ativo"),
    DISABLED("Inativo");

    private final String label;

    CostCenterStatus(final String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
