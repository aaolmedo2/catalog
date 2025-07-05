package com.banquito.core.loan.catalog.controller;

import com.banquito.core.loan.catalog.dto.TiposComisionesDTO;
import com.banquito.core.loan.catalog.exception.CreateException;
import com.banquito.core.loan.catalog.exception.DeleteException;
import com.banquito.core.loan.catalog.exception.EntityNotFoundException;
import com.banquito.core.loan.catalog.mapper.TiposComisionesMapper;
import com.banquito.core.loan.catalog.model.TiposComisiones;
import com.banquito.core.loan.catalog.service.TiposComisionesService;
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
@RequestMapping("/api/v1/tipos-comisiones")
@Tag(name = "Tipos de Comisiones", description = "API para gestionar tipos de comisiones")
public class TiposComisionesController {

    private final TiposComisionesService tiposComisionesService;

    public TiposComisionesController(TiposComisionesService tiposComisionesService) {
        this.tiposComisionesService = tiposComisionesService;
    }

    @Operation(summary = "Obtener todos los tipos de comisiones activos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipos de comisiones encontrados", content = @Content(schema = @Schema(implementation = TiposComisionesDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<TiposComisionesDTO>> findAll() {
        log.info("Petición para obtener todos los tipos de comisiones");
        List<TiposComisionesDTO> tiposComisiones = this.tiposComisionesService.findAll()
                .stream()
                .map(TiposComisionesMapper::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tiposComisiones);
    }

    @Operation(summary = "Obtener un tipo de comisión por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de comisión encontrado", content = @Content(schema = @Schema(implementation = TiposComisionesDTO.class))),
            @ApiResponse(responseCode = "404", description = "Tipo de comisión no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<TiposComisionesDTO> findById(
            @Parameter(description = "ID del tipo de comisión", required = true) @PathVariable String id) {
        log.info("Petición para obtener tipo de comisión con ID: {}", id);
        try {
            TiposComisiones tipoComision = this.tiposComisionesService.findById(id);
            return ResponseEntity.ok(TiposComisionesMapper.mapToDTO(tipoComision));
        } catch (EntityNotFoundException e) {
            log.error("Error al obtener tipo de comisión: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Crear un nuevo tipo de comisión")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo de comisión creado", content = @Content(schema = @Schema(implementation = TiposComisionesDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<TiposComisionesDTO> create(@Valid @RequestBody TiposComisionesDTO tipoComisionDTO) {
        log.info("Petición para crear tipo de comisión: {}", tipoComisionDTO);
        try {
            TiposComisiones tipoComision = TiposComisionesMapper.mapToEntity(tipoComisionDTO);
            TiposComisiones savedTipoComision = this.tiposComisionesService.create(tipoComision);
            return ResponseEntity.status(HttpStatus.CREATED).body(TiposComisionesMapper.mapToDTO(savedTipoComision));
        } catch (CreateException e) {
            log.error("Error al crear tipo de comisión: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Eliminar un tipo de comisión por su ID (eliminación lógica)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tipo de comisión eliminado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Tipo de comisión no encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Error al eliminar tipo de comisión", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del tipo de comisión", required = true) @PathVariable String id) {
        log.info("Petición para eliminar tipo de comisión con ID: {}", id);
        try {
            this.tiposComisionesService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            log.error("Error al eliminar tipo de comisión: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (DeleteException e) {
            log.error("Error al eliminar tipo de comisión: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
