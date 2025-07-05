package com.banquito.core.loan.catalog.controller;

import com.banquito.core.loan.catalog.dto.PrestamosDTO;
import com.banquito.core.loan.catalog.exception.CreateException;
import com.banquito.core.loan.catalog.exception.DeleteException;
import com.banquito.core.loan.catalog.exception.EntityNotFoundException;
import com.banquito.core.loan.catalog.exception.UpdateException;
import com.banquito.core.loan.catalog.mapper.PrestamosMapper;
import com.banquito.core.loan.catalog.model.Prestamos;
import com.banquito.core.loan.catalog.service.PrestamosService;
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
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/prestamos")
@Tag(name = "Préstamos", description = "API para gestionar préstamos")
public class PrestamosController {

    private final PrestamosService prestamosService;

    public PrestamosController(PrestamosService prestamosService) {
        this.prestamosService = prestamosService;
    }

    @Operation(summary = "Obtener todos los préstamos activos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Préstamos encontrados", content = @Content(schema = @Schema(implementation = PrestamosDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<PrestamosDTO>> findAll() {
        log.info("Petición para obtener todos los préstamos");
        List<PrestamosDTO> prestamos = this.prestamosService.findAll()
                .stream()
                .map(PrestamosMapper::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(prestamos);
    }

    @Operation(summary = "Obtener un préstamo por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Préstamo encontrado", content = @Content(schema = @Schema(implementation = PrestamosDTO.class))),
            @ApiResponse(responseCode = "404", description = "Préstamo no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PrestamosDTO> findById(
            @Parameter(description = "ID del préstamo", required = true) @PathVariable String id) {
        log.info("Petición para obtener préstamo con ID: {}", id);
        try {
            Prestamos prestamo = this.prestamosService.findById(id);
            return ResponseEntity.ok(PrestamosMapper.mapToDTO(prestamo));
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
            return ResponseEntity.status(HttpStatus.CREATED).body(PrestamosMapper.mapToDTO(savedPrestamo));
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
            return ResponseEntity.ok(PrestamosMapper.mapToDTO(updatedPrestamo));
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
