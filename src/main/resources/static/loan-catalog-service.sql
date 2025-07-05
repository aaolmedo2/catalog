/*==============================================================*/
/* Table: garantias                                             */
/*==============================================================*/
create table garantias (
   id_garantia          SERIAL not null,
   tipo_garantia        VARCHAR(20)          not null
      constraint ckc_tipo_garantia check (tipo_garantia in ('HIPOTECA','PRENDARIA','PERSONAL')),
   descripcion          VARCHAR(200)         not null,
   valor                NUMERIC(15,2)        not null,
   estado               VARCHAR(15)          not null
      constraint ckc_estado_garantia check (estado in ('ACTIVO','INACTIVO')),
   version              NUMERIC(9)           not null,
   constraint pk_garantias primary key (id_garantia)
);

comment on table garantias is
'Tabla que almacena informacion sobre las garantias asociadas a los tipos de prestamos.';

comment on column garantias.id_garantia is
'Identificador unico de la garantia.';

comment on column garantias.tipo_garantia is
'Tipo de garantia (HIPOTECA, PRENDARIA, PERSONAL).';

comment on column garantias.descripcion is
'Descripcion detallada de la garantia.';

comment on column garantias.valor is
'Valor monetario de la garantia.';

comment on column garantias.estado is
'Estado actual de la garantia (ACTIVO, INACTIVO).';

comment on column garantias.version is
'Numero de version del registro para control de concurrencia.';

/*==============================================================*/
/* Table: prestamos                                             */
/*==============================================================*/
create table prestamos (
   id_prestamo          SERIAL not null,
   id_tipo_prestamo     INT                  not null,
   id_moneda            VARCHAR(3)           not null,
   nombre               VARCHAR(100)         not null,
   descripcion          VARCHAR(200)         not null,
   fecha_modificacion   TIMESTAMP            not null default CURRENT_DATE,
   base_calculo         VARCHAR(10)          not null
      constraint ckc_base_calculo check (base_calculo in ('30/360','31/365')),
   tasa_interes         NUMERIC(5,2)         not null,
   monto_minimo         NUMERIC(15,2)        not null,
   monto_maximo         NUMERIC(15,2)        not null,
   plazo_minimo_meses   NUMERIC(2)           not null,
   plazo_maximo_meses   NUMERIC(2)           not null,
   tipo_amortizacion    VARCHAR(20)          not null,
   estado               VARCHAR(15)          not null default 'SOLICITADO'
      constraint ckc_estado_prestamo check (estado in ('ACTIVO','INACTIVO','SOLICITADO')),
   version              NUMERIC(9)           not null,
   id_seguro            INT                  null,
   id_tipo_comision     INT                  null,
   constraint pk_prestamos primary key (id_prestamo)
);

comment on table prestamos is
'Tabla que define los productos de prestamo disponibles.';

comment on column prestamos.id_prestamo is
'Identificador unico del producto de prestamo.';

comment on column prestamos.id_tipo_prestamo is
'Clave foranea que referencia el tipo de prestamo general.';

comment on column prestamos.id_moneda is
'Moneda en la que se otorga el prestamo.';

comment on column prestamos.nombre is
'Nombre del producto de prestamo.';

comment on column prestamos.descripcion is
'Descripcion del producto de prestamo.';

comment on column prestamos.fecha_modificacion is
'Fecha de ultima modificacion del registro.';

comment on column prestamos.base_calculo is
'Base de calculo para intereses (30/360, 31/365).';

comment on column prestamos.tasa_interes is
'Tasa de interes nominal para este producto.';

comment on column prestamos.monto_minimo is
'Monto minimo que se puede solicitar.';

comment on column prestamos.monto_maximo is
'Monto maximo que se puede solicitar.';

comment on column prestamos.plazo_minimo_meses is
'Plazo minimo en meses para este prestamo.';

comment on column prestamos.plazo_maximo_meses is
'Plazo maximo en meses para este prestamo.';

comment on column prestamos.tipo_amortizacion is
'Tipo de amortizacion predeterminado (FRANCES, AMERICANO, ALEMAN).';

comment on column prestamos.estado is
'Estado del producto (ACTIVO, INACTIVO, SOLICITADO).';

comment on column prestamos.version is
'Numero de version del registro para control de concurrencia.';

/*==============================================================*/
/* Table: seguros                                               */
/*==============================================================*/
create table seguros (
   id_seguro            SERIAL not null,
   tipo_seguro          VARCHAR(30)          not null
      constraint ckc_tipo_seguro check (tipo_seguro in ('VIDA','DESEMPLEO','PROTECCION_PAGOS','DESGRAVAMEN','INCENDIOS')),
   compania             VARCHAR(100)         not null,
   monto_asegurado      NUMERIC(15,2)        not null,
   fecha_inicio         DATE                 not null,
   fecha_fin            DATE                 not null,
   estado               VARCHAR(15)          not null default 'ACTIVO'
      constraint ckc_estado_seguro check (estado in ('ACTIVO','VENCIDO','CANCELADO','INACTIVO')),
   version              NUMERIC(9)           not null,
   constraint pk_seguros primary key (id_seguro)
);

comment on table seguros is
'Tabla que define los diferentes tipos de seguros asociables a prestamos.';

comment on column seguros.id_seguro is
'Identificador unico del seguro.';

comment on column seguros.tipo_seguro is
'Tipo de seguro (VIDA, DESEMPLEO, PROTECCION_PAGOS, DESGRAVAMEN, INCENDIOS).';

comment on column seguros.compania is
'Compania de seguros que emite la poliza.';

comment on column seguros.monto_asegurado is
'Monto total asegurado por esta poliza.';

comment on column seguros.fecha_inicio is
'Fecha de inicio de la cobertura.';

comment on column seguros.fecha_fin is
'Fecha de fin de la cobertura.';

comment on column seguros.estado is
'Estado actual del seguro (ACTIVO, VENCIDO, CANCELADO, INACTIVO).';

comment on column seguros.version is
'Numero de version del registro para control de concurrencia.';

/*==============================================================*/
/* Table: tipos_comisiones                                      */
/*==============================================================*/
create table tipos_comisiones (
   id_tipo_comision     SERIAL not null,
   tipo                 VARCHAR(30)          not null
      constraint ckc_tipo_comision check (tipo in ('ORIGINACION','PAGO ATRASADO','PREPAGO','MODIFICACION','SERVICIO ADICIONAL')),
   nombre               VARCHAR(100)         not null,
   descripcion          VARCHAR(200)         null,
   tipo_calculo         VARCHAR(20)          not null
      constraint ckc_tipo_calculo_comision check (tipo_calculo in ('PORCENTAJE','FIJO')),
   monto                NUMERIC(15,2)        not null,
   estado               VARCHAR(15)          not null default 'ACTIVO'
      constraint ckc_estado_tipo_comision check (estado in ('ACTIVO','INACTIVO')),
   version              NUMERIC(9)           not null default 1,
   constraint pk_tipos_comisiones primary key (id_tipo_comision)
);

comment on table tipos_comisiones is
'Catalogo de tipos de comisiones o cargos aplicables a prestamos.';

comment on column tipos_comisiones.id_tipo_comision is
'Identificador unico del tipo de comision.';

comment on column tipos_comisiones.tipo is
'Categoria de comision (ORIGINACION, PAGO ATRASADO, PREPAGO, etc).';

comment on column tipos_comisiones.nombre is
'Nombre descriptivo del tipo de comision.';

comment on column tipos_comisiones.descripcion is
'Descripcion detallada de la comision y su aplicacion.';

comment on column tipos_comisiones.tipo_calculo is
'Metodo de calculo (PORCENTAJE del monto o valor FIJO).';

comment on column tipos_comisiones.monto is
'Valor base para el calculo (porcentaje o monto fijo).';

comment on column tipos_comisiones.estado is
'Estado del tipo de comision (ACTIVO, INACTIVO).';

comment on column tipos_comisiones.version is
'Version del registro para control de concurrencia.';

/*==============================================================*/
/* Table: tipos_prestamos                                       */
/*==============================================================*/
create table tipos_prestamos (
   id_tipo_prestamo     SERIAL not null,
   id_moneda            VARCHAR(3)           not null,
   nombre               VARCHAR(50)          not null,
   descripcion          VARCHAR(100)         not null,
   requisitos           VARCHAR(300)         not null,
   tipo_cliente         VARCHAR(15)          not null
      constraint ckc_tipo_cliente check (tipo_cliente in ('PERSONA','EMPRESA','AMBOS')),
   fecha_creacion       TIMESTAMP            not null default CURRENT_DATE,
   fecha_modificacion   TIMESTAMP            not null default CURRENT_DATE,
   estado               VARCHAR(15)          not null default 'ACTIVO'
      constraint ckc_estado_tipo_prestamo check (estado in ('ACTIVO','INACTIVO')),
   version              NUMERIC(9)           not null,
   esquema_amortizacion VARCHAR(15)          null,
   id_garantia          INT                  null,
   constraint pk_tipos_prestamos primary key (id_tipo_prestamo)
);

comment on table tipos_prestamos is
'Tabla que define las categorias o tipos generales de prestamos.';

comment on column tipos_prestamos.id_tipo_prestamo is
'Identificador unico del tipo de prestamo.';

comment on column tipos_prestamos.id_moneda is
'Moneda predeterminada para este tipo de prestamo.';

comment on column tipos_prestamos.nombre is
'Nombre del tipo de prestamo (Personal, Hipotecario, etc).';

comment on column tipos_prestamos.descripcion is
'Descripcion del tipo de prestamo.';

comment on column tipos_prestamos.requisitos is
'Requisitos necesarios para solicitar este tipo de prestamo.';

comment on column tipos_prestamos.tipo_cliente is
'Tipo de cliente permitido (PERSONA, EMPRESA, AMBOS).';

comment on column tipos_prestamos.fecha_creacion is
'Fecha de creacion del registro.';

comment on column tipos_prestamos.fecha_modificacion is
'Fecha de ultima modificacion del registro.';

comment on column tipos_prestamos.estado is
'Estado del tipo de prestamo (ACTIVO, INACTIVO).';

comment on column tipos_prestamos.version is
'Numero de version del registro para control de concurrencia.';

