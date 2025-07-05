package com.banquito.core.loan.catalog.enums;

public enum TipoGarantiaEnum {
    HIPOTECA("HIPOTECA"),
    PRENDARIA("PRENDARIA"),
    PERSONAL("PERSONAL");

    private final String valor;

    TipoGarantiaEnum(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}