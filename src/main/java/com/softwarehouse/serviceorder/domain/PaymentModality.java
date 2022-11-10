package com.softwarehouse.serviceorder.domain;

public enum PaymentModality {
    MONEY("Dinheiro"),
    CREDIT_CARD("Cartão de Crédito"),
    DEBIT_CARD("Débito"),
    BILL("Boleto");

    private final String label;

    PaymentModality(final String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
