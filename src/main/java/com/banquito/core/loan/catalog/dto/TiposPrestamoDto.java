package com.banquito.core.loan.catalog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TiposPrestamoDto {

    private String id;
    private String idMoneda;
    private String nombre;
    private String descripcion;
    private String requisitos;
    private String tipoCliente;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private String estado;
    private Long version;
    private String esquemaAmortizacion;
    private Integer idGarantia;
}
