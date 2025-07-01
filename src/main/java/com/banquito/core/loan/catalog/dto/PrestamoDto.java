package com.banquito.core.loan.catalog.dto;

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
public class PrestamoDto {

    private String id;
    private Integer idTipoPrestamo;
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
    private Integer idSeguro;
    private Integer idTipoComision;
    private Integer tipoPrestamo;
    private String estado;
    private Long version;
}
