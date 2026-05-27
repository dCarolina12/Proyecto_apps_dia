package com.docufacil.docufacil.controller;

import com.docufacil.docufacil.model.AuditLog;
import com.docufacil.docufacil.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
@CrossOrigin(origins = "*")
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<AuditLog>> getLogsByOrganization(@PathVariable Long organizationId) {
        List<AuditLog> logs = auditLogService.getLogsByOrganization(organizationId);
        return ResponseEntity.ok(logs);
    }
}