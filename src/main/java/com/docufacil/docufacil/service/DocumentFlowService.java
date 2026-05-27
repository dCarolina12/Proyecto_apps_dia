package com.docufacil.docufacil.service;

import com.docufacil.docufacil.model.Document;
import com.docufacil.docufacil.model.DocumentFlow;
import com.docufacil.docufacil.model.User;
import com.docufacil.docufacil.repository.DocumentFlowRepository;
import com.docufacil.docufacil.repository.DocumentRepository;
import com.docufacil.docufacil.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DocumentFlowService {

    @Autowired
    private DocumentFlowRepository documentFlowRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private EmailService emailService;

    @Transactional
    public Document changeStatus(Long documentId, String newStatus, String comments, Long userId) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String previousStatus = document.getStatus();

        DocumentFlow flow = new DocumentFlow();
        flow.setDocument(document);
        flow.setPreviousStatus(previousStatus);
        flow.setNewStatus(newStatus.toUpperCase());
        flow.setComments(comments);
        flow.setActionDate(LocalDateTime.now());
        flow.setUser(user);
        documentFlowRepository.save(flow);

        document.setStatus(newStatus.toUpperCase());
        Document savedDocument = documentRepository.save(document);

        auditLogService.logEvent(
                "CAMBIO_ESTADO",
                "Documento '" + document.getTitle() + "' (ID: " + documentId + ") pasó de " + previousStatus + " a " + newStatus.toUpperCase(),
                userId
        );

        emailService.sendStatusChangeEmail(
                user.getEmail(),
                user.getName(),
                document.getTitle(),
                previousStatus,
                newStatus.toUpperCase(),
                comments
        );

        return savedDocument;
    }

    public List<DocumentFlow> getDocumentHistory(Long documentId) {
        return documentFlowRepository.findByDocumentIdOrderByActionDateAsc(documentId);
    }
}