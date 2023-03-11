package com.softwarehouse.serviceorder.contexts.shared.models;

public enum Situation {
    ACTIVE("ativo"),
    INACTIVE("inativo");

    private final String label;

    Situation(String label) {
        this.label = label;
    }

    public String getLabel(){return this.label;}

}
