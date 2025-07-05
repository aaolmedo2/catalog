package com.banquito.core.loan.catalog.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Document(collection = "garantias")
public class Garantias {

    @Id
    private String id;

    private String tipoGarantia;
    private String descripcion;
    private BigDecimal valor;
    private String estado;
    private Long version;

    public Garantias(String id) {
        this.id = id;
    }

}
