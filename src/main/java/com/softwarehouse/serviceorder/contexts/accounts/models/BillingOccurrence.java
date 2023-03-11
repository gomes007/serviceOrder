package com.softwarehouse.serviceorder.contexts.accounts.models;

public enum BillingOccurrence {

    ONCE("Único"),
    INSTALLMENT("Parcelado/recorrente");

    private final String label;

    BillingOccurrence(final String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

}
