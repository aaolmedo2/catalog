package com.banquito.core.loan.catalog.controller;

import com.banquito.core.loan.catalog.dto.GarantiasDTO;
import com.banquito.core.loan.catalog.exception.CreateException;
import com.banquito.core.loan.catalog.exception.DeleteException;
import com.banquito.core.loan.catalog.exception.EntityNotFoundException;
import com.banquito.core.loan.catalog.mapper.GarantiasMapper;
import com.banquito.core.loan.catalog.model.Garantias;
import com.banquito.core.loan.catalog.service.GarantiasService;
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
@CrossOrigin(origins = "*")
@RequestMapping("/api/catalogo/v1/garantias")
@Tag(name = "Garantías", description = "API para gestionar garantías")
public class GarantiasController {

    private final GarantiasService garantiasService;

    public GarantiasController(GarantiasService garantiasService) {
        this.garantiasService = garantiasService;
    }

    @Operation(summary = "Obtener todas las garantías activas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Garantías encontradas", content = @Content(schema = @Schema(implementation = GarantiasDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<GarantiasDTO>> findAll() {
        log.info("Petición para obtener todas las garantías");
        List<GarantiasDTO> garantias = this.garantiasService.findAll()
                .stream()
                .map(GarantiasMapper::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(garantias);
    }

    @Operation(summary = "Obtener una garantía por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Garantía encontrada", content = @Content(schema = @Schema(implementation = GarantiasDTO.class))),
            @ApiResponse(responseCode = "404", description = "Garantía no encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<GarantiasDTO> findById(
            @Parameter(description = "ID de la garantía", required = true) @PathVariable String id) {
        log.info("Petición para obtener garantía con ID: {}", id);
        try {
            Garantias garantia = this.garantiasService.findById(id);
            return ResponseEntity.ok(GarantiasMapper.mapToDTO(garantia));
        } catch (EntityNotFoundException e) {
            log.error("Error al obtener garantía: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Crear una nueva garantía")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Garantía creada", content = @Content(schema = @Schema(implementation = GarantiasDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<GarantiasDTO> create(@Valid @RequestBody GarantiasDTO garantiaDTO) {
        log.info("Petición para crear garantía: {}", garantiaDTO);
        try {
            Garantias garantia = GarantiasMapper.mapToEntity(garantiaDTO);
            Garantias savedGarantia = this.garantiasService.create(garantia);
            return ResponseEntity.status(HttpStatus.CREATED).body(GarantiasMapper.mapToDTO(savedGarantia));
        } catch (CreateException e) {
            log.error("Error al crear garantía: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Eliminar una garantía por su ID (eliminación lógica)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Garantía eliminada", content = @Content),
            @ApiResponse(responseCode = "404", description = "Garantía no encontrada", content = @Content),
            @ApiResponse(responseCode = "400", description = "Error al eliminar garantía", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la garantía", required = true) @PathVariable String id) {
        log.info("Petición para eliminar garantía con ID: {}", id);
        try {
            this.garantiasService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            log.error("Error al eliminar garantía: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (DeleteException e) {
            log.error("Error al eliminar garantía: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
