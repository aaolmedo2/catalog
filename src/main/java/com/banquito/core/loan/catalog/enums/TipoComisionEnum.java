package com.banquito.core.loan.catalog.enums;

public enum TipoComisionEnum {
    ORIGINACION("ORIGINACION"),
    PAGO_ATRASADO("PAGO ATRASADO"),
    PREPAGO("PREPAGO"),
    MODIFICACION("MODIFICACION"),
    SERVICIO_ADICIONAL("SERVICIO ADICIONAL");

    private final String valor;

    TipoComisionEnum(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}