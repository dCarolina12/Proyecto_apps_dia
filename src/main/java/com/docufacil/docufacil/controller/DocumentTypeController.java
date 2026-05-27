package com.docufacil.docufacil.controller;

import com.docufacil.docufacil.model.DocumentType;
import com.docufacil.docufacil.service.DocumentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/document-types")
@CrossOrigin(origins = "*") 
public class DocumentTypeController {

    @Autowired
    private DocumentTypeService documentTypeService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Map<String, Object> payload) {
        try {
            String name = (String) payload.get("name");
            String description = (String) payload.get("description");
            Long organizationId = ((Number) payload.get("organizationId")).longValue();

            DocumentType newType = documentTypeService.createDocumentType(name, description, organizationId);
            return new ResponseEntity<>(newType, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<DocumentType>> getByOrganization(@PathVariable Long organizationId) {
        List<DocumentType> types = documentTypeService.getTypesByOrganization(organizationId);
        return ResponseEntity.ok(types);
    }
}