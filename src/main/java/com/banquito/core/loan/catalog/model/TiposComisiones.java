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
@Document(collection = "tipos_comisiones")
public class TiposComisiones {

    @Id
    private String id;

    private String tipo;
    private String nombre;
    private String descripcion;
    private String tipoCalculo;
    private BigDecimal monto;
    private String estado;
    private Long version;

    public TiposComisiones(String id) {
        this.id = id;
    }

}
