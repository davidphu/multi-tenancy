package com.davidphu.catalog.controller;

import com.davidphu.catalog.jpa.CatalogEntity;
import com.davidphu.catalog.service.CatalogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
public class CatalogController {
    private CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping(value = "/catalog")
    public ResponseEntity<List<CatalogEntity>> listCatalog() {
        List<CatalogEntity> all = catalogService.findAll();
        return ResponseEntity.ok()
            .contentType(APPLICATION_JSON)
            .body(all);
    }

    @GetMapping(value = "/catalog/{itemId}")
    public ResponseEntity<CatalogEntity> viewItemById(@PathVariable Long itemId) {
        CatalogEntity item = catalogService.findById(itemId).orElse(null);
        return ResponseEntity.ok()
            .contentType(APPLICATION_JSON)
            .body(item);
    }
}
