package com.banquito.core.loan.catalog.controller;

import com.banquito.core.loan.catalog.dto.TiposPrestamoDto;
import com.banquito.core.loan.catalog.service.TiposPrestamosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tipos-prestamos")
@CrossOrigin(origins = "*")
public class TiposPrestamosController {

    private final TiposPrestamosService service;

    public TiposPrestamosController(TiposPrestamosService service) {
        this.service = service;
    }

    /**
     * Obtener tipos de préstamo activos para mostrar catálogo
     */
    @GetMapping("/activos")
    public ResponseEntity<List<TiposPrestamoDto>> getActiveTiposPrestamos() {
        try {
            List<TiposPrestamoDto> tipos = service.findByEstado("ACTIVO");
            return ResponseEntity.ok(tipos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener tipos de préstamo por tipo de cliente
     */
    @GetMapping("/cliente/{tipoCliente}")
    public ResponseEntity<List<TiposPrestamoDto>> getTiposByCliente(@PathVariable String tipoCliente) {
        try {
            List<TiposPrestamoDto> tipos = service.findByTipoClienteAndEstado(tipoCliente, "ACTIVO");
            return ResponseEntity.ok(tipos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener tipos de préstamo por estado específico
     */
    @GetMapping
    public ResponseEntity<List<TiposPrestamoDto>> getTiposByEstado(@RequestParam String estado) {
        try {
            List<TiposPrestamoDto> tipos = service.findByEstado(estado);
            return ResponseEntity.ok(tipos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener un tipo de préstamo específico por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<TiposPrestamoDto> getTipoPrestamoById(@PathVariable String id) {
        try {
            Optional<TiposPrestamoDto> tipo = service.findById(id);
            return tipo.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Crear nuevo tipo de préstamo
     */
    @PostMapping
    public ResponseEntity<TiposPrestamoDto> createTipoPrestamo(@RequestBody TiposPrestamoDto dto) {
        try {
            TiposPrestamoDto created = service.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Actualizar tipo de préstamo existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<TiposPrestamoDto> updateTipoPrestamo(@PathVariable String id,
            @RequestBody TiposPrestamoDto dto) {
        try {
            TiposPrestamoDto updated = service.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Cambiar estado de tipo de préstamo
     */
    @PatchMapping("/{id}/estado")
    public ResponseEntity<TiposPrestamoDto> changeStatus(@PathVariable String id, @RequestParam String estado) {
        try {
            TiposPrestamoDto updated = service.changeStatus(id, estado);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
