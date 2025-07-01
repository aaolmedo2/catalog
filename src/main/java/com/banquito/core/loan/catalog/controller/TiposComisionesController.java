package com.banquito.core.loan.catalog.controller;

import com.banquito.core.loan.catalog.dto.TipoComisionDto;
import com.banquito.core.loan.catalog.service.TiposComisionesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tipos-comisiones")
@CrossOrigin(origins = "*")
public class TiposComisionesController {

    private final TiposComisionesService service;

    public TiposComisionesController(TiposComisionesService service) {
        this.service = service;
    }

    /**
     * Obtener tipos de comisión activos
     */
    @GetMapping("/activos")
    public ResponseEntity<List<TipoComisionDto>> getActiveComisiones() {
        try {
            List<TipoComisionDto> comisiones = service.findActiveComisiones();
            return ResponseEntity.ok(comisiones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener tipos de comisión por tipo
     */
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<TipoComisionDto>> getComisionesByTipo(@PathVariable String tipo) {
        try {
            List<TipoComisionDto> comisiones = service.findByTipoAndEstado(tipo, "ACTIVO");
            return ResponseEntity.ok(comisiones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener tipos de comisión por método de cálculo
     */
    @GetMapping("/calculo/{tipoCalculo}")
    public ResponseEntity<List<TipoComisionDto>> getComisionesByTipoCalculo(@PathVariable String tipoCalculo) {
        try {
            List<TipoComisionDto> comisiones = service.findByTipoCalculoAndEstado(tipoCalculo, "ACTIVO");
            return ResponseEntity.ok(comisiones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener un tipo de comisión específico por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<TipoComisionDto> getTipoComisionById(@PathVariable String id) {
        try {
            Optional<TipoComisionDto> comision = service.findById(id);
            return comision.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Crear nuevo tipo de comisión
     */
    @PostMapping
    public ResponseEntity<TipoComisionDto> createTipoComision(@RequestBody TipoComisionDto dto) {
        try {
            TipoComisionDto created = service.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Actualizar tipo de comisión existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<TipoComisionDto> updateTipoComision(@PathVariable String id,
            @RequestBody TipoComisionDto dto) {
        try {
            TipoComisionDto updated = service.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Cambiar estado de tipo de comisión
     */
    @PatchMapping("/{id}/estado")
    public ResponseEntity<TipoComisionDto> changeStatus(@PathVariable String id, @RequestParam String estado) {
        try {
            TipoComisionDto updated = service.changeStatus(id, estado);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
