package com.softwarehouse.serviceorder.contexts.order.models;

public enum OrderStatus {

    OPEN("Em aberto"),
    IN_PROGRESS("Em andamento"),
    FINISHED("Concretizada"),
    CANCELED("Cancelada");

    private final String label;

    OrderStatus(final String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

}
