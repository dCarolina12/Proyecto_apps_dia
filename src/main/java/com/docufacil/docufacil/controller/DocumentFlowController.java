package com.docufacil.docufacil.controller;

import com.docufacil.docufacil.model.Document;
import com.docufacil.docufacil.model.DocumentFlow;
import com.docufacil.docufacil.service.DocumentFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/workflows")
@CrossOrigin(origins = "*")
public class DocumentFlowController {

    @Autowired
    private DocumentFlowService documentFlowService;

    
    @PostMapping("/change-status")
    public ResponseEntity<?> changeStatus(@RequestBody Map<String, Object> payload) {
        try {
            Long documentId = ((Number) payload.get("documentId")).longValue();
            String newStatus = (String) payload.get("newStatus");
            String comments = (String) payload.get("comments");
            Long userId = ((Number) payload.get("userId")).longValue();

            Document updatedDocument = documentFlowService.changeStatus(documentId, newStatus, comments, userId);
            return ResponseEntity.ok(Map.of(
                    "message", "Estado actualizado con éxito",
                    "currentStatus", updatedDocument.getStatus()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/history/{documentId}")
    public ResponseEntity<List<DocumentFlow>> getHistory(@PathVariable Long documentId) {
        List<DocumentFlow> history = documentFlowService.getDocumentHistory(documentId);
        return ResponseEntity.ok(history);
    }
}