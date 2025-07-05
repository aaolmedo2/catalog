package com.banquito.core.loan.catalog.client;

import com.banquito.core.loan.catalog.dto.TiposPrestamosDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "tipos-prestamos-service", url = "${loans.transaction.url:http://localhost:8084}")
public interface TiposPrestamosClient {

    @GetMapping("/api/v1/tipos-prestamos")
    ResponseEntity<List<TiposPrestamosDTO>> findAll();

    @GetMapping("/api/v1/tipos-prestamos/{id}")
    ResponseEntity<TiposPrestamosDTO> findById(@PathVariable("id") String id);

    @PostMapping("/api/v1/tipos-prestamos")
    ResponseEntity<TiposPrestamosDTO> create(@RequestBody TiposPrestamosDTO tipoPrestamoDTO);
}
