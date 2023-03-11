package com.softwarehouse.serviceorder.contexts.payment.models;

public enum PaymentMethodAvailability {
    SELLING("Somente Contas a Receber"),
    BUYING("Somente Contas a Pagar"),
    BOTH("Contas a pagar e receber");

    private final String label;

    PaymentMethodAvailability(final String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
