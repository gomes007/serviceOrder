package com.softwarehouse.serviceorder.domain;

public enum BillingEntity {

    PROVIDER("Fornecedor"),
    EMPLOYEE("Funcionário"),
    CUSTOMER("Cliente"),
    OTHER("Outro");

    private final String label;

    BillingEntity(final String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

}
