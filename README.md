# Catalog loans

## Port
8088

## 🔐 Endpoints
### 🏦 GARANTIAS

#### 📥 GET

```http
  GET http://localhost:8088/api/v1/garantias
  GET http://localhost:8088/api/v1/seguros
  GET http://localhost:8088/api/v1/tipos-comisiones
  GET http://localhost:8088/api/v1/tipos-prestamos
  GET http://localhost:8088/api/v1/prestamos
```

#### ➕ POST

```http
  POST http://localhost:8088/api/v1/garantias
  POST http://localhost:8088/api/v1/seguros
  POST http://localhost:8088/api/v1/tipos-comisiones
  POST http://localhost:8088/api/v1/tipos-prestamos
  POST http://localhost:8088/api/v1/prestamos
```

#### Request body
```javascript
{
    "tipoGarantia": "PERSONAL",
    "descripcion": "GARANTIA PERSONAL",
    "valor": 1000
}
{
    "tipoSeguro": "VIDA",
    "compania": "VIDA NUEVA",
    "montoAsegurado": 50000,
    "fechaInicio": "2025-02-02",
    "fechaFin": "2025-12-12"
}
{
    "tipo": "PREPAGO",
    "nombre": "PREPAGO COMISON EXTRA",
    "descripcion": "ESTA COMISION ES EXCLUSIVA PARA PREPAGO COMISON EXTRA",
    "tipoCalculo": "PORCENTAJE",
    "monto": 50
}
{
    "idMoneda": "USD",
    "nombre": "VEHICULAR",
    "descripcion": "PRESTAMO RELACIONADO CON VEHICULOS DE CUATRO LLANTAS.",
    "requisitos": "BURO DE CREDITO ACEPTABLE Y RESIDENCIA EN ECUADOR.",
    "tipoCliente": "PERSONA",
    "esquemaAmortizacion": "FRANCES",
    "idGarantia": "6869710205df90df6e894cc5"
}
{
    "idTipoPrestamo": "68697fb3f5b8faf03b4dc3b9",
    "idMoneda": "USD",
    "nombre": "PRESTAMO VEHCULAR NUEVO",
    "descripcion": "PRESTAMO PARA PERSONAS QUE DESEEN ADQUIRIR UN AUTO NUEVO DE CASA.",
    "baseCalculo": "30/360",
    "tasaInteres": "10",
    "montoMinimo": 10000,
    "montoMaximo": 30000,
    "plazoMinimoMeses": 12,
    "plazoMaximoMeses": 36,
    "tipoAmortizacion": "FRANCES",
    "idSeguro": "686971ff05df90df6e894cc6",
    "idTipoComision": "686972a605df90df6e894cc7"
}
```


## Constrain for catalog loans

| TABLE                         | ATTRIBUTE        | CONSTRAINT                                                                                       | ENUM                        |
|:------------------------------|:------------------|:------------------------------------------------------------------------------------------------------|:-----------------------------|
| comisiones_prestamos          | `estado`          | ▫️ 'ACTIVO'<br>▫️ 'INACTIVO'                                                                      | EstadoGeneralEnum           |
| tipos_comisiones              | `tipo`            | ▫️ 'ORIGINACION'<br>▫️ 'PAGO ATRASADO'<br>▫️ 'PREPAGO'<br>▫️ 'MODIFICACION'<br>▫️ 'SERVICIO ADICIONAL' | TipoComisionEnum            |
| tipos_comisiones              | `tipo_calculo`    | ▫️ 'PORCENTAJE'<br>▫️ 'FIJO'                                                                      | TipoCalculoComisionEnum     |
| tipos_comisiones              | `estado`          | ▫️ 'ACTIVO'<br>▫️ 'INACTIVO'                                                                      | EstadoGeneralEnum           |
| esquemas_amortizacion         | `nombre`          | ▫️ 'FRANCES'<br>▫️ 'AMERICANO'<br>▫️ 'ALEMAN'                                                     | EsquemaAmortizacionEnum     |
| esquemas_amortizacion         | `estado`          | ▫️ 'ACTIVO'<br>▫️ 'INACTIVO'                                                                      | EstadoGeneralEnum           |
| garantias                     | `tipo_garantia`   | ▫️ 'HIPOTECA'<br>▫️ 'PRENDARIA'<br>▫️ 'PERSONAL'                                                  | TipoGarantiaEnum            |
| garantias                     | `estado`          | ▫️ 'ACTIVO'<br>▫️ 'INACTIVO'                                                                      | EstadoGeneralEnum           |
| garantias_tipos_prestamos     | `estado`          | ▫️ 'ACTIVO'<br>▫️ 'INACTIVO'                                                                      | EstadoGeneralEnum           |
| tipos_prestamos               | `tipo_cliente`    | ▫️ 'PERSONA'<br>▫️ 'EMPRESA'<br>▫️ 'AMBOS'                                                        | TipoClienteEnum             |
| tipos_prestamos               | `estado`          | ▫️ 'ACTIVO'<br>▫️ 'INACTIVO'                                                                      | EstadoGeneralEnum           |
| prestamos                     | `base_calculo`    | ▫️ '30/360'<br>▫️ '31/365'                                                                        | BaseCalculoEnum             |
| prestamos                     | `estado`          | ▫️ 'ACTIVO'<br>▫️ 'INACTIVO'<br>▫️ 'SOLICITADO'                                                   | EstadoPrestamoEnum          |
| seguros                       | `tipo_seguro`     | ▫️ 'VIDA'<br>▫️ 'DESEMPLEO'<br>▫️ 'PROTECCION_PAGOS'<br>▫️ 'DESGRAVAMEN'<br>▫️ 'INCENDIOS'         | TipoSeguroEnum              |
| seguros                       | `estado`          | ▫️ 'ACTIVO'<br>▫️ 'VENCIDO'<br>▫️ 'CANCELADO'<br>▫️ 'INACTIVO'                                    | EstadoSeguroEnum            |
| seguros_prestamos             | `estado`          | ▫️ 'ACTIVO'<br>▫️ 'INACTIVO'                                                                      | EstadoGeneralEnum           |
