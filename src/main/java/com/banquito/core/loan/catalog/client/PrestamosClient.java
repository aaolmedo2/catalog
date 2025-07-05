package com.banquito.core.loan.catalog.client;

import com.banquito.core.loan.catalog.dto.PrestamosDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "prestamos-service", url = "${loans.transaction.url:http://localhost:8084}")
public interface PrestamosClient {

    @GetMapping("/api/v1/prestamos")
    ResponseEntity<List<PrestamosDTO>> findAll();

    @GetMapping("/api/v1/prestamos/{id}")
    ResponseEntity<PrestamosDTO> findById(@PathVariable("id") String id);

    @PostMapping("/api/v1/prestamos")
    ResponseEntity<PrestamosDTO> create(@RequestBody PrestamosDTO prestamoDTO);
}
