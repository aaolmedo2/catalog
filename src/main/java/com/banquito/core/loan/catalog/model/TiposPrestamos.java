package com.banquito.core.loan.catalog.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Document(collection = "tipos_prestamos")
public class TiposPrestamos {

    @Id
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
    private String idGarantia;

    public TiposPrestamos(String id) {
        this.id = id;
    }

}
