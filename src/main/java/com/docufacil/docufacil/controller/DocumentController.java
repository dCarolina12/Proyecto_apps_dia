package com.docufacil.docufacil.controller;

import com.docufacil.docufacil.dto.DocumentResponseDTO;
import com.docufacil.docufacil.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;


import java.util.List;

@RestController

@RequestMapping("/api/documents")
@CrossOrigin(origins = "http://localhost:4200")

public class DocumentController {

    @Autowired
    private DocumentService documentService;


    @PostMapping("/upload")
    public ResponseEntity<DocumentResponseDTO> uploadDocument(
            @RequestParam("title") String title,
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId) {

        DocumentResponseDTO response = documentService.uploadDocument(title, file, userId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<DocumentResponseDTO>> getDocumentsByOrganization(
            @PathVariable Long organizationId) {

        List<DocumentResponseDTO> documents = documentService.getDocumentsByOrganization(organizationId);
        return ResponseEntity.ok(documents);
    }


    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
        byte[] fileData = documentService.downloadFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"document_download\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(fileData);
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateDocumentStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        String nuevoStatus = body.get("status");
        documentService.updateStatus(id, nuevoStatus);
        return ResponseEntity.ok("Estado actualizado exitosamente");
    }
}