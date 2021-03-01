package com.davidphu.catalog.service;

import com.davidphu.catalog.jpa.CatalogEntity;
import com.davidphu.catalog.jpa.CatalogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatalogService {
    private CatalogRepository catalogRepository;

    public CatalogService(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    public List<CatalogEntity> findAll() {
        return catalogRepository.findAll();
    }

    public Optional<CatalogEntity> findById(Long itemId) {
        return catalogRepository.findById(itemId);
    }
}
