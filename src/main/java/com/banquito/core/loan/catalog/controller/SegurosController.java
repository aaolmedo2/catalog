package com.banquito.core.loan.catalog.controller;

import com.banquito.core.loan.catalog.dto.SeguroDto;
import com.banquito.core.loan.catalog.service.SegurosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/seguros")
@CrossOrigin(origins = "*")
public class SegurosController {

    private final SegurosService service;

    public SegurosController(SegurosService service) {
        this.service = service;
    }

    /**
     * Obtener seguros activos
     */
    @GetMapping("/activos")
    public ResponseEntity<List<SeguroDto>> getActiveSeguros() {
        try {
            List<SeguroDto> seguros = service.findActiveSeguros();
            return ResponseEntity.ok(seguros);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener seguros vigentes (no expirados)
     */
    @GetMapping("/vigentes")
    public ResponseEntity<List<SeguroDto>> getSegurosVigentes() {
        try {
            List<SeguroDto> seguros = service.findVigentes();
            return ResponseEntity.ok(seguros);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener seguros por tipo
     */
    @GetMapping("/tipo/{tipoSeguro}")
    public ResponseEntity<List<SeguroDto>> getSegurosByTipo(@PathVariable String tipoSeguro) {
        try {
            List<SeguroDto> seguros = service.findByTipoAndEstado(tipoSeguro, "ACTIVO");
            return ResponseEntity.ok(seguros);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener seguros por compañía aseguradora
     */
    @GetMapping("/compania/{compania}")
    public ResponseEntity<List<SeguroDto>> getSegurosByCompania(@PathVariable String compania) {
        try {
            List<SeguroDto> seguros = service.findByCompaniaAndEstado(compania, "ACTIVO");
            return ResponseEntity.ok(seguros);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener un seguro específico por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<SeguroDto> getSeguroById(@PathVariable String id) {
        try {
            Optional<SeguroDto> seguro = service.findById(id);
            return seguro.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Crear nuevo seguro
     */
    @PostMapping
    public ResponseEntity<SeguroDto> createSeguro(@RequestBody SeguroDto dto) {
        try {
            SeguroDto created = service.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Actualizar seguro existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<SeguroDto> updateSeguro(@PathVariable String id, @RequestBody SeguroDto dto) {
        try {
            SeguroDto updated = service.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Cambiar estado de seguro
     */
    @PatchMapping("/{id}/estado")
    public ResponseEntity<SeguroDto> changeStatus(@PathVariable String id, @RequestParam String estado) {
        try {
            SeguroDto updated = service.changeStatus(id, estado);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
