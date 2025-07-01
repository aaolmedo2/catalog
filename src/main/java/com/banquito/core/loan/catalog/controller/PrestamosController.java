package com.banquito.core.loan.catalog.controller;

import com.banquito.core.loan.catalog.dto.PrestamoDto;
import com.banquito.core.loan.catalog.service.PrestamosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/prestamos")
@CrossOrigin(origins = "*")
public class PrestamosController {

    private final PrestamosService service;

    public PrestamosController(PrestamosService service) {
        this.service = service;
    }

    /**
     * Obtener préstamos activos
     */
    @GetMapping("/activos")
    public ResponseEntity<List<PrestamoDto>> getActivePrestamos() {
        try {
            List<PrestamoDto> prestamos = service.findActiveLoans();
            return ResponseEntity.ok(prestamos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener préstamos por tipo de préstamo
     */
    @GetMapping("/tipo/{idTipoPrestamo}")
    public ResponseEntity<List<PrestamoDto>> getPrestamosByTipo(@PathVariable Integer idTipoPrestamo) {
        try {
            List<PrestamoDto> prestamos = service.findByTipoPrestamoAndEstado(idTipoPrestamo, "ACTIVO");
            return ResponseEntity.ok(prestamos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener préstamos por moneda
     */
    @GetMapping("/moneda/{idMoneda}")
    public ResponseEntity<List<PrestamoDto>> getPrestamosByMoneda(@PathVariable String idMoneda) {
        try {
            List<PrestamoDto> prestamos = service.findByMonedaAndEstado(idMoneda, "ACTIVO");
            return ResponseEntity.ok(prestamos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Buscar préstamos por monto disponible
     */
    @GetMapping("/monto/{monto}")
    public ResponseEntity<List<PrestamoDto>> getPrestamosByMonto(@PathVariable BigDecimal monto) {
        try {
            List<PrestamoDto> prestamos = service.findByMontoRange(monto, "ACTIVO");
            return ResponseEntity.ok(prestamos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Buscar préstamos por plazo disponible
     */
    @GetMapping("/plazo/{plazoMeses}")
    public ResponseEntity<List<PrestamoDto>> getPrestamosByPlazo(@PathVariable Integer plazoMeses) {
        try {
            List<PrestamoDto> prestamos = service.findByPlazoRange(plazoMeses, "ACTIVO");
            return ResponseEntity.ok(prestamos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener un préstamo específico por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<PrestamoDto> getPrestamoById(@PathVariable String id) {
        try {
            Optional<PrestamoDto> prestamo = service.findById(id);
            return prestamo.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Crear nuevo préstamo
     */
    @PostMapping
    public ResponseEntity<PrestamoDto> createPrestamo(@RequestBody PrestamoDto dto) {
        try {
            PrestamoDto created = service.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Actualizar préstamo existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<PrestamoDto> updatePrestamo(@PathVariable String id, @RequestBody PrestamoDto dto) {
        try {
            PrestamoDto updated = service.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Cambiar estado de préstamo
     */
    @PatchMapping("/{id}/estado")
    public ResponseEntity<PrestamoDto> changeStatus(@PathVariable String id, @RequestParam String estado) {
        try {
            PrestamoDto updated = service.changeStatus(id, estado);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
