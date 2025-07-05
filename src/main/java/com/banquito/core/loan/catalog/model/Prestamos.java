package com.banquito.core.loan.catalog.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Document(collection = "prestamos")
public class Prestamos {

    @Id
    private String id;

    private String idTipoPrestamo;
    private String idMoneda;
    private String nombre;
    private String descripcion;
    private LocalDateTime fechaModificacion;
    private String baseCalculo;
    private BigDecimal tasaInteres;
    private BigDecimal montoMinimo;
    private BigDecimal montoMaximo;
    private Integer plazoMinimoMeses;
    private Integer plazoMaximoMeses;
    private String tipoAmortizacion;
    private String idSeguro;
    private String idTipoComision;
    private String estado;
    private Long version;

    public Prestamos(String id) {
        this.id = id;
    }

}
