package com.banquito.core.loan.catalog.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PrestamosDTO {

    private String id;

    @NotNull(message = "ID de tipo préstamo es requerido")
    private String idTipoPrestamo;

    @NotEmpty(message = "ID de moneda es requerido")
    private String idMoneda;

    @NotEmpty(message = "Nombre es requerido")
    private String nombre;

    @NotEmpty(message = "Descripción es requerida")
    private String descripcion;

    private LocalDateTime fechaModificacion;

    @NotEmpty(message = "Base de cálculo es requerida")
    private String baseCalculo;

    @NotNull(message = "Tasa de interés es requerida")
    @Positive(message = "Tasa de interés debe ser mayor a cero")
    private BigDecimal tasaInteres;

    @NotNull(message = "Monto mínimo es requerido")
    @Positive(message = "Monto mínimo debe ser mayor a cero")
    private BigDecimal montoMinimo;

    @NotNull(message = "Monto máximo es requerido")
    @Positive(message = "Monto máximo debe ser mayor a cero")
    private BigDecimal montoMaximo;

    @NotNull(message = "Plazo mínimo en meses es requerido")
    @Positive(message = "Plazo mínimo en meses debe ser mayor a cero")
    private Integer plazoMinimoMeses;

    @NotNull(message = "Plazo máximo en meses es requerido")
    @Positive(message = "Plazo máximo en meses debe ser mayor a cero")
    private Integer plazoMaximoMeses;

    @NotEmpty(message = "Tipo de amortización es requerido")
    private String tipoAmortizacion;

    @NotNull(message = "ID de seguro es requerido")
    private String idSeguro;

    @NotNull(message = "ID de tipo comisión es requerido")
    private String idTipoComision;

    private String estado;
    private Long version;
}
