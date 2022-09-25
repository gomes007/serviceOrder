package com.softwarehouse.serviceorder.model;

public enum PaymentTypes {
    ONE_TIME("À vista"),
    INSTALLMENT("Parcelado");

    private final String label;

    PaymentTypes(final String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
