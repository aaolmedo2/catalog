package com.banquito.core.loan.catalog.controller;

import com.banquito.core.loan.catalog.dto.SegurosDTO;
import com.banquito.core.loan.catalog.exception.CreateException;
import com.banquito.core.loan.catalog.exception.DeleteException;
import com.banquito.core.loan.catalog.exception.EntityNotFoundException;
import com.banquito.core.loan.catalog.mapper.SegurosMapper;
import com.banquito.core.loan.catalog.model.Seguros;
import com.banquito.core.loan.catalog.service.SegurosService;
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
@RequestMapping("/api/catalogo/v1/seguros")
@Tag(name = "Seguros", description = "API para gestionar seguros")
public class SegurosController {

    private final SegurosService segurosService;

    public SegurosController(SegurosService segurosService) {
        this.segurosService = segurosService;
    }

    @Operation(summary = "Obtener todos los seguros activos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seguros encontrados", content = @Content(schema = @Schema(implementation = SegurosDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<SegurosDTO>> findAll() {
        log.info("Petición para obtener todos los seguros");
        List<SegurosDTO> seguros = this.segurosService.findAll()
                .stream()
                .map(SegurosMapper::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(seguros);
    }

    @Operation(summary = "Obtener un seguro por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seguro encontrado", content = @Content(schema = @Schema(implementation = SegurosDTO.class))),
            @ApiResponse(responseCode = "404", description = "Seguro no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<SegurosDTO> findById(
            @Parameter(description = "ID del seguro", required = true) @PathVariable String id) {
        log.info("Petición para obtener seguro con ID: {}", id);
        try {
            Seguros seguro = this.segurosService.findById(id);
            return ResponseEntity.ok(SegurosMapper.mapToDTO(seguro));
        } catch (EntityNotFoundException e) {
            log.error("Error al obtener seguro: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Crear un nuevo seguro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Seguro creado", content = @Content(schema = @Schema(implementation = SegurosDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<SegurosDTO> create(@Valid @RequestBody SegurosDTO seguroDTO) {
        log.info("Petición para crear seguro: {}", seguroDTO);
        try {
            Seguros seguro = SegurosMapper.mapToEntity(seguroDTO);
            Seguros savedSeguro = this.segurosService.create(seguro);
            return ResponseEntity.status(HttpStatus.CREATED).body(SegurosMapper.mapToDTO(savedSeguro));
        } catch (CreateException e) {
            log.error("Error al crear seguro: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Eliminar un seguro por su ID (eliminación lógica)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Seguro eliminado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Seguro no encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Error al eliminar seguro", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del seguro", required = true) @PathVariable String id) {
        log.info("Petición para eliminar seguro con ID: {}", id);
        try {
            this.segurosService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            log.error("Error al eliminar seguro: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (DeleteException e) {
            log.error("Error al eliminar seguro: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
