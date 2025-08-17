package com.banquito.core.loan.catalog.controller;

import com.banquito.core.loan.catalog.dto.PrestamosDTO;
import com.banquito.core.loan.catalog.exception.CreateException;
import com.banquito.core.loan.catalog.exception.DeleteException;
import com.banquito.core.loan.catalog.exception.EntityNotFoundException;
import com.banquito.core.loan.catalog.exception.UpdateException;
import com.banquito.core.loan.catalog.mapper.PrestamosMapper;
import com.banquito.core.loan.catalog.model.Prestamos;
import com.banquito.core.loan.catalog.model.Seguros;
import com.banquito.core.loan.catalog.model.TiposComisiones;
import com.banquito.core.loan.catalog.model.TiposPrestamos;
import com.banquito.core.loan.catalog.service.PrestamosService;
import com.banquito.core.loan.catalog.service.SegurosService;
import com.banquito.core.loan.catalog.service.TiposComisionesService;
import com.banquito.core.loan.catalog.service.TiposPrestamosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/catalogo/v1/prestamos")
@CrossOrigin(origins = "*")
@Tag(name = "Préstamos", description = "API para gestionar préstamos")
public class PrestamosController {

    private final PrestamosService prestamosService;
    private final TiposPrestamosService tiposPrestamosService;
    private final SegurosService segurosService;
    private final TiposComisionesService tiposComisionesService;

    public PrestamosController(
            PrestamosService prestamosService,
            TiposPrestamosService tiposPrestamosService,
            SegurosService segurosService,
            TiposComisionesService tiposComisionesService) {
        this.prestamosService = prestamosService;
        this.tiposPrestamosService = tiposPrestamosService;
        this.segurosService = segurosService;
        this.tiposComisionesService = tiposComisionesService;
    }

    @Operation(summary = "Obtener todos los préstamos activos", description = "Devuelve todos los préstamos con los detalles completos de tipo préstamo, seguros y tipo comisión asociados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Préstamos encontrados con detalles completos de entidades relacionadas", content = @Content(schema = @Schema(implementation = PrestamosDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<PrestamosDTO>> findAll() {
        log.info("Petición para obtener todos los préstamos");
        List<PrestamosDTO> prestamos = new ArrayList<>();

        for (Prestamos prestamo : this.prestamosService.findAll()) {
            try {
                // Obtener entidades relacionadas
                TiposPrestamos tipoPrestamo = null;
                Seguros seguro = null;
                TiposComisiones tipoComision = null;

                try {
                    tipoPrestamo = this.tiposPrestamosService.findById(prestamo.getIdTipoPrestamo());
                } catch (EntityNotFoundException e) {
                    log.warn("Tipo de préstamo no encontrado para el préstamo {}: {}", prestamo.getId(),
                            e.getMessage());
                }

                try {
                    seguro = this.segurosService.findById(prestamo.getIdSeguro());
                } catch (EntityNotFoundException e) {
                    log.warn("Seguro no encontrado para el préstamo {}: {}", prestamo.getId(), e.getMessage());
                }

                try {
                    tipoComision = this.tiposComisionesService.findById(prestamo.getIdTipoComision());
                } catch (EntityNotFoundException e) {
                    log.warn("Tipo de comisión no encontrado para el préstamo {}: {}", prestamo.getId(),
                            e.getMessage());
                }

                // Mapear con las relaciones disponibles
                prestamos.add(PrestamosMapper.mapToDTOWithRelations(prestamo, tipoPrestamo, seguro, tipoComision));
            } catch (Exception e) {
                log.error("Error al obtener detalles para el préstamo {}: {}", prestamo.getId(), e.getMessage());
                // Si hay error, incluir la versión básica
                prestamos.add(PrestamosMapper.mapToDTO(prestamo));
            }
        }

        return ResponseEntity.ok(prestamos);
    }

    @Operation(summary = "Obtener un préstamo por su ID", description = "Devuelve un préstamo con los detalles completos de tipo préstamo, seguros y tipo comisión asociados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Préstamo encontrado con detalles completos de entidades relacionadas", content = @Content(schema = @Schema(implementation = PrestamosDTO.class))),
            @ApiResponse(responseCode = "404", description = "Préstamo no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PrestamosDTO> findById(
            @Parameter(description = "ID del préstamo", required = true) @PathVariable String id) {
        log.info("Petición para obtener préstamo con ID: {}", id);
        try {
            Prestamos prestamo = this.prestamosService.findById(id);

            // Obtener entidades relacionadas
            TiposPrestamos tipoPrestamo = null;
            Seguros seguro = null;
            TiposComisiones tipoComision = null;

            try {
                tipoPrestamo = this.tiposPrestamosService.findById(prestamo.getIdTipoPrestamo());
            } catch (EntityNotFoundException e) {
                log.warn("Tipo de préstamo no encontrado para el préstamo {}: {}", id, e.getMessage());
            }

            try {
                seguro = this.segurosService.findById(prestamo.getIdSeguro());
            } catch (EntityNotFoundException e) {
                log.warn("Seguro no encontrado para el préstamo {}: {}", id, e.getMessage());
            }

            try {
                tipoComision = this.tiposComisionesService.findById(prestamo.getIdTipoComision());
            } catch (EntityNotFoundException e) {
                log.warn("Tipo de comisión no encontrado para el préstamo {}: {}", id, e.getMessage());
            }

            // Devolver DTO con relaciones
            return ResponseEntity
                    .ok(PrestamosMapper.mapToDTOWithRelations(prestamo, tipoPrestamo, seguro, tipoComision));

        } catch (EntityNotFoundException e) {
            log.error("Error al obtener préstamo: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Crear un nuevo préstamo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Préstamo creado", content = @Content(schema = @Schema(implementation = PrestamosDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<PrestamosDTO> create(@Valid @RequestBody PrestamosDTO prestamoDTO) {
        log.info("Petición para crear préstamo: {}", prestamoDTO);
        try {
            Prestamos prestamo = PrestamosMapper.mapToEntity(prestamoDTO);
            Prestamos savedPrestamo = this.prestamosService.create(prestamo);

            // Obtener entidades relacionadas para la respuesta
            TiposPrestamos tipoPrestamo = null;
            Seguros seguro = null;
            TiposComisiones tipoComision = null;

            try {
                tipoPrestamo = this.tiposPrestamosService.findById(savedPrestamo.getIdTipoPrestamo());
            } catch (EntityNotFoundException e) {
                log.warn("Tipo de préstamo no encontrado para el préstamo creado: {}", e.getMessage());
            }

            try {
                seguro = this.segurosService.findById(savedPrestamo.getIdSeguro());
            } catch (EntityNotFoundException e) {
                log.warn("Seguro no encontrado para el préstamo creado: {}", e.getMessage());
            }

            try {
                tipoComision = this.tiposComisionesService.findById(savedPrestamo.getIdTipoComision());
            } catch (EntityNotFoundException e) {
                log.warn("Tipo de comisión no encontrado para el préstamo creado: {}", e.getMessage());
            }

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(PrestamosMapper.mapToDTOWithRelations(savedPrestamo, tipoPrestamo, seguro, tipoComision));

        } catch (CreateException e) {
            log.error("Error al crear préstamo: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Actualizar un préstamo por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Préstamo actualizado", content = @Content(schema = @Schema(implementation = PrestamosDTO.class))),
            @ApiResponse(responseCode = "404", description = "Préstamo no encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<PrestamosDTO> update(
            @Parameter(description = "ID del préstamo", required = true) @PathVariable String id,
            @Valid @RequestBody PrestamosDTO prestamoDTO) {
        log.info("Petición para actualizar préstamo con ID {}: {}", id, prestamoDTO);
        try {
            Prestamos prestamo = PrestamosMapper.mapToEntity(prestamoDTO);
            Prestamos updatedPrestamo = this.prestamosService.update(id, prestamo);

            // Obtener entidades relacionadas para la respuesta
            TiposPrestamos tipoPrestamo = null;
            Seguros seguro = null;
            TiposComisiones tipoComision = null;

            try {
                tipoPrestamo = this.tiposPrestamosService.findById(updatedPrestamo.getIdTipoPrestamo());
            } catch (EntityNotFoundException e) {
                log.warn("Tipo de préstamo no encontrado para el préstamo actualizado: {}", e.getMessage());
            }

            try {
                seguro = this.segurosService.findById(updatedPrestamo.getIdSeguro());
            } catch (EntityNotFoundException e) {
                log.warn("Seguro no encontrado para el préstamo actualizado: {}", e.getMessage());
            }

            try {
                tipoComision = this.tiposComisionesService.findById(updatedPrestamo.getIdTipoComision());
            } catch (EntityNotFoundException e) {
                log.warn("Tipo de comisión no encontrado para el préstamo actualizado: {}", e.getMessage());
            }

            return ResponseEntity
                    .ok(PrestamosMapper.mapToDTOWithRelations(updatedPrestamo, tipoPrestamo, seguro, tipoComision));
        } catch (EntityNotFoundException e) {
            log.error("Error al actualizar préstamo: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (UpdateException e) {
            log.error("Error al actualizar préstamo: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Eliminar un préstamo por su ID (eliminación lógica)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Préstamo eliminado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Préstamo no encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Error al eliminar préstamo", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del préstamo", required = true) @PathVariable String id) {
        log.info("Petición para eliminar préstamo con ID: {}", id);
        try {
            this.prestamosService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            log.error("Error al eliminar préstamo: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (DeleteException e) {
            log.error("Error al eliminar préstamo: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
