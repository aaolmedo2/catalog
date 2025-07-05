package com.banquito.core.loan.catalog.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Document(collection = "seguros")
public class Seguros {

    @Id
    private String id;

    private String tipoSeguro;
    private String compania;
    private BigDecimal montoAsegurado;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;
    private Long version;

    public Seguros(String id) {
        this.id = id;
    }

}
