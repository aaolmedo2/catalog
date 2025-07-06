package com.banquito.core.loan.catalog.controller;

import com.banquito.core.loan.catalog.dto.TiposPrestamosDTO;
import com.banquito.core.loan.catalog.exception.CreateException;
import com.banquito.core.loan.catalog.exception.DeleteException;
import com.banquito.core.loan.catalog.exception.EntityNotFoundException;
import com.banquito.core.loan.catalog.exception.UpdateException;
import com.banquito.core.loan.catalog.mapper.TiposPrestamosMapper;
import com.banquito.core.loan.catalog.model.Garantias;
import com.banquito.core.loan.catalog.model.TiposPrestamos;
import com.banquito.core.loan.catalog.service.GarantiasService;
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
@RequestMapping("/api/v1/tipos-prestamos")
@Tag(name = "Tipos de Préstamos", description = "API para gestionar tipos de préstamos")
public class TiposPrestamosController {

    private final TiposPrestamosService tiposPrestamosService;
    private final GarantiasService garantiasService;

    public TiposPrestamosController(TiposPrestamosService tiposPrestamosService, GarantiasService garantiasService) {
        this.tiposPrestamosService = tiposPrestamosService;
        this.garantiasService = garantiasService;
    }

    @Operation(summary = "Obtener todos los tipos de préstamos activos", description = "Devuelve todos los tipos de préstamos con los detalles completos de las garantías asociadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipos de préstamos encontrados con detalles de garantías", content = @Content(schema = @Schema(implementation = TiposPrestamosDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<TiposPrestamosDTO>> findAll() {
        log.info("Petición para obtener todos los tipos de préstamos");
        List<TiposPrestamosDTO> tiposPrestamos = new ArrayList<>();

        for (TiposPrestamos tipoPrestamo : this.tiposPrestamosService.findAll()) {
            try {
                // Intentar obtener la garantía asociada
                Garantias garantia = this.garantiasService.findById(tipoPrestamo.getIdGarantia());
                tiposPrestamos.add(TiposPrestamosMapper.mapToDTOWithGarantia(tipoPrestamo, garantia));
            } catch (EntityNotFoundException e) {
                log.warn("Garantía no encontrada para el tipo de préstamo {}: {}", tipoPrestamo.getId(),
                        e.getMessage());
                // Si no se encuentra la garantía, incluir el tipo de préstamo sin detalles de
                // garantía
                tiposPrestamos.add(TiposPrestamosMapper.mapToDTO(tipoPrestamo));
            }
        }

        return ResponseEntity.ok(tiposPrestamos);
    }

    @Operation(summary = "Obtener un tipo de préstamo por su ID", description = "Devuelve el tipo de préstamo con todos los detalles de la garantía asociada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de préstamo encontrado con detalles completos de garantía", content = @Content(schema = @Schema(implementation = TiposPrestamosDTO.class))),
            @ApiResponse(responseCode = "404", description = "Tipo de préstamo no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<TiposPrestamosDTO> findById(
            @Parameter(description = "ID del tipo de préstamo", required = true) @PathVariable String id) {
        log.info("Petición para obtener tipo de préstamo con ID: {}", id);
        try {
            TiposPrestamos tipoPrestamo = this.tiposPrestamosService.findById(id);
            // Fetch the associated Garantia
            String idGarantia = tipoPrestamo.getIdGarantia();
            try {
                Garantias garantia = this.garantiasService.findById(idGarantia);
                return ResponseEntity.ok(TiposPrestamosMapper.mapToDTOWithGarantia(tipoPrestamo, garantia));
            } catch (EntityNotFoundException e) {
                log.warn("Garantía no encontrada para el tipo de préstamo {}: {}", id, e.getMessage());
                // Return the DTO without garantia details
                return ResponseEntity.ok(TiposPrestamosMapper.mapToDTO(tipoPrestamo));
            }
        } catch (EntityNotFoundException e) {
            log.error("Error al obtener tipo de préstamo: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Crear un nuevo tipo de préstamo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo de préstamo creado", content = @Content(schema = @Schema(implementation = TiposPrestamosDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<TiposPrestamosDTO> create(@Valid @RequestBody TiposPrestamosDTO tipoPrestamoDTO) {
        log.info("Petición para crear tipo de préstamo: {}", tipoPrestamoDTO);
        try {
            TiposPrestamos tipoPrestamo = TiposPrestamosMapper.mapToEntity(tipoPrestamoDTO);
            TiposPrestamos savedTipoPrestamo = this.tiposPrestamosService.create(tipoPrestamo);

            try {
                // Obtener la garantía asociada para incluirla en la respuesta
                Garantias garantia = this.garantiasService.findById(savedTipoPrestamo.getIdGarantia());
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(TiposPrestamosMapper.mapToDTOWithGarantia(savedTipoPrestamo, garantia));
            } catch (EntityNotFoundException e) {
                log.warn("Garantía no encontrada para el tipo de préstamo creado: {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.CREATED).body(TiposPrestamosMapper.mapToDTO(savedTipoPrestamo));
            }
        } catch (CreateException e) {
            log.error("Error al crear tipo de préstamo: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Actualizar un tipo de préstamo por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de préstamo actualizado", content = @Content(schema = @Schema(implementation = TiposPrestamosDTO.class))),
            @ApiResponse(responseCode = "404", description = "Tipo de préstamo no encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<TiposPrestamosDTO> update(
            @Parameter(description = "ID del tipo de préstamo", required = true) @PathVariable String id,
            @Valid @RequestBody TiposPrestamosDTO tipoPrestamoDTO) {
        log.info("Petición para actualizar tipo de préstamo con ID {}: {}", id, tipoPrestamoDTO);
        try {
            TiposPrestamos tipoPrestamo = TiposPrestamosMapper.mapToEntity(tipoPrestamoDTO);
            TiposPrestamos updatedTipoPrestamo = this.tiposPrestamosService.update(id, tipoPrestamo);

            try {
                // Obtener la garantía asociada para incluirla en la respuesta
                Garantias garantia = this.garantiasService.findById(updatedTipoPrestamo.getIdGarantia());
                return ResponseEntity.ok(TiposPrestamosMapper.mapToDTOWithGarantia(updatedTipoPrestamo, garantia));
            } catch (EntityNotFoundException e) {
                log.warn("Garantía no encontrada para el tipo de préstamo actualizado: {}", e.getMessage());
                return ResponseEntity.ok(TiposPrestamosMapper.mapToDTO(updatedTipoPrestamo));
            }
        } catch (EntityNotFoundException e) {
            log.error("Error al actualizar tipo de préstamo: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (UpdateException e) {
            log.error("Error al actualizar tipo de préstamo: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Eliminar un tipo de préstamo por su ID (eliminación lógica)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tipo de préstamo eliminado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Tipo de préstamo no encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Error al eliminar tipo de préstamo", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del tipo de préstamo", required = true) @PathVariable String id) {
        log.info("Petición para eliminar tipo de préstamo con ID: {}", id);
        try {
            this.tiposPrestamosService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            log.error("Error al eliminar tipo de préstamo: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (DeleteException e) {
            log.error("Error al eliminar tipo de préstamo: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
