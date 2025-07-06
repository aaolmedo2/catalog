package com.banquito.core.loan.catalog.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TiposPrestamosDTO {

    private String id;

    @NotEmpty(message = "ID de moneda es requerido")
    private String idMoneda;

    @NotEmpty(message = "Nombre es requerido")
    private String nombre;

    @NotEmpty(message = "Descripción es requerida")
    private String descripcion;

    @NotEmpty(message = "Requisitos son requeridos")
    private String requisitos;

    @NotEmpty(message = "Tipo de cliente es requerido")
    private String tipoCliente;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private String estado;
    private Long version;

    @NotEmpty(message = "Esquema de amortización es requerido")
    private String esquemaAmortizacion;

    @NotNull(message = "ID de garantía es requerido")
    private String idGarantia;

    // Objeto completo de la garantía para respuestas
    private GarantiasDTO garantia;
}
