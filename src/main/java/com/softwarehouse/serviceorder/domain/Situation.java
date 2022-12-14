package com.softwarehouse.serviceorder.domain;

public enum Situation {
    ACTIVE("ativo"),
    INACTIVE("inativo");

    private final String label;

    Situation(String label) {
        this.label = label;
    }

    public String getLabel(){return this.label;}

}
