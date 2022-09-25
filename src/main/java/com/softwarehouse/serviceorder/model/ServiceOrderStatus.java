package com.softwarehouse.serviceorder.model;

public enum ServiceOrderStatus {

    OPEN("Em aberto"),
    IN_PROGRESS("Em andamento"),
    FINISHED("Concretizada"),
    CANCELED("Cancelada");

    private final String label;

    ServiceOrderStatus(final String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

}
