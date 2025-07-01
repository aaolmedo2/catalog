package com.banquito.core.loan.catalog.controller;

import com.banquito.core.loan.catalog.dto.GarantiaDto;
import com.banquito.core.loan.catalog.service.GarantiasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/garantias")
@CrossOrigin(origins = "*")
public class GarantiasController {

    private final GarantiasService service;

    public GarantiasController(GarantiasService service) {
        this.service = service;
    }

    /**
     * Obtener garantías activas
     */
    @GetMapping("/activas")
    public ResponseEntity<List<GarantiaDto>> getActiveGarantias() {
        try {
            List<GarantiaDto> garantias = service.findActiveGarantias();
            return ResponseEntity.ok(garantias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener garantías por tipo
     */
    @GetMapping("/tipo/{tipoGarantia}")
    public ResponseEntity<List<GarantiaDto>> getGarantiasByTipo(@PathVariable String tipoGarantia) {
        try {
            List<GarantiaDto> garantias = service.findByTipoAndEstado(tipoGarantia, "ACTIVO");
            return ResponseEntity.ok(garantias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener una garantía específica por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<GarantiaDto> getGarantiaById(@PathVariable String id) {
        try {
            Optional<GarantiaDto> garantia = service.findById(id);
            return garantia.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Crear nueva garantía
     */
    @PostMapping
    public ResponseEntity<GarantiaDto> createGarantia(@RequestBody GarantiaDto dto) {
        try {
            GarantiaDto created = service.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Actualizar garantía existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<GarantiaDto> updateGarantia(@PathVariable String id, @RequestBody GarantiaDto dto) {
        try {
            GarantiaDto updated = service.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Cambiar estado de garantía
     */
    @PatchMapping("/{id}/estado")
    public ResponseEntity<GarantiaDto> changeStatus(@PathVariable String id, @RequestParam String estado) {
        try {
            GarantiaDto updated = service.changeStatus(id, estado);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
