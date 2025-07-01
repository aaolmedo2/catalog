# Loan Catalog Service

Este servicio gestiona el catálogo de préstamos, incluyendo tipos de préstamos, garantías, seguros y comisiones.

## Servicios Implementados

### 1. TiposPrestamosService
Gestiona los tipos de préstamos disponibles.

**Endpoints:**
- `GET /api/v1/tipos-prestamos/activos` - Obtener tipos de préstamo activos
- `GET /api/v1/tipos-prestamos/cliente/{tipoCliente}` - Filtrar por tipo de cliente
- `GET /api/v1/tipos-prestamos?estado={estado}` - Filtrar por estado
- `GET /api/v1/tipos-prestamos/{id}` - Obtener tipo por ID
- `POST /api/v1/tipos-prestamos` - Crear nuevo tipo
- `PUT /api/v1/tipos-prestamos/{id}` - Actualizar tipo
- `PATCH /api/v1/tipos-prestamos/{id}/estado?estado={estado}` - Cambiar estado

### 2. PrestamosService
Gestiona los productos de préstamo específicos.

**Endpoints:**
- `GET /api/v1/prestamos/activos` - Obtener préstamos activos
- `GET /api/v1/prestamos/tipo/{idTipoPrestamo}` - Filtrar por tipo de préstamo
- `GET /api/v1/prestamos/moneda/{idMoneda}` - Filtrar por moneda
- `GET /api/v1/prestamos/monto/{monto}` - Buscar por monto disponible
- `GET /api/v1/prestamos/plazo/{plazoMeses}` - Buscar por plazo disponible
- `GET /api/v1/prestamos/{id}` - Obtener préstamo por ID
- `POST /api/v1/prestamos` - Crear nuevo préstamo
- `PUT /api/v1/prestamos/{id}` - Actualizar préstamo
- `PATCH /api/v1/prestamos/{id}/estado?estado={estado}` - Cambiar estado

### 3. GarantiasService
Gestiona el catálogo de garantías.

**Endpoints:**
- `GET /api/v1/garantias/activas` - Obtener garantías activas
- `GET /api/v1/garantias/tipo/{tipoGarantia}` - Filtrar por tipo
- `GET /api/v1/garantias/{id}` - Obtener garantía por ID
- `POST /api/v1/garantias` - Crear nueva garantía
- `PUT /api/v1/garantias/{id}` - Actualizar garantía
- `PATCH /api/v1/garantias/{id}/estado?estado={estado}` - Cambiar estado

### 4. SegurosService
Gestiona el catálogo de seguros.

**Endpoints:**
- `GET /api/v1/seguros/activos` - Obtener seguros activos
- `GET /api/v1/seguros/vigentes` - Obtener seguros vigentes (no expirados)
- `GET /api/v1/seguros/tipo/{tipoSeguro}` - Filtrar por tipo
- `GET /api/v1/seguros/compania/{compania}` - Filtrar por compañía aseguradora
- `GET /api/v1/seguros/{id}` - Obtener seguro por ID
- `POST /api/v1/seguros` - Crear nuevo seguro
- `PUT /api/v1/seguros/{id}` - Actualizar seguro
- `PATCH /api/v1/seguros/{id}/estado?estado={estado}` - Cambiar estado

### 5. TiposComisionesService
Gestiona el catálogo de tipos de comisiones.

**Endpoints:**
- `GET /api/v1/tipos-comisiones/activos` - Obtener comisiones activas
- `GET /api/v1/tipos-comisiones/tipo/{tipo}` - Filtrar por tipo
- `GET /api/v1/tipos-comisiones/calculo/{tipoCalculo}` - Filtrar por método de cálculo
- `GET /api/v1/tipos-comisiones/{id}` - Obtener comisión por ID
- `POST /api/v1/tipos-comisiones` - Crear nueva comisión
- `PUT /api/v1/tipos-comisiones/{id}` - Actualizar comisión
- `PATCH /api/v1/tipos-comisiones/{id}/estado?estado={estado}` - Cambiar estado

## Características de la Arquitectura

### Métodos Reales y Prácticos
Los métodos implementados son de uso real en una aplicación bancaria:
- **Filtrado por estado**: Solo se muestran productos activos al cliente
- **Búsqueda por criterios específicos**: Montos, plazos, tipos de cliente
- **Gestión de versiones**: Control de versiones para auditoría
- **Estados de entidad**: Gestión del ciclo de vida de los productos

### Base de Datos MongoDB
- Configuración para MongoDB
- Repositories con query methods específicos
- Consultas optimizadas con anotación @Query

### DTOs y Mappers
- Separación clara entre modelo de datos y DTOs de transferencia
- Mappers para conversión bidireccional
- Validaciones en DTOs (listo para agregar cuando se resuelvan dependencias)

### Control de Errores
- Manejo de excepciones en controllers
- Responses HTTP apropiados
- Clase ResponseDto para standardizar respuestas

## Configuración

### MongoDB
```properties
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=loan_catalog
```

### Puerto del Servicio
```properties
server.port=8080
server.servlet.context-path=/catalog
```

## Uso Típico

1. **Consultar tipos de préstamo para un cliente específico:**
   ```
   GET /catalog/api/v1/tipos-prestamos/cliente/PERSONAL
   ```

2. **Buscar préstamos que permitan un monto específico:**
   ```
   GET /catalog/api/v1/prestamos/monto/50000
   ```

3. **Obtener seguros vigentes:**
   ```
   GET /catalog/api/v1/seguros/vigentes
   ```

4. **Consultar garantías por tipo:**
   ```
   GET /catalog/api/v1/garantias/tipo/HIPOTECARIA
   ```

Los servicios están diseñados pensando en casos de uso reales de una aplicación bancaria, evitando métodos como "obtener todos" que no son prácticos en producción.
