package com.docufacil.docufacil.service;

import com.docufacil.docufacil.model.AuditLog;
import com.docufacil.docufacil.model.User;
import com.docufacil.docufacil.repository.AuditLogRepository;
import com.docufacil.docufacil.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void logEvent(String action, String details, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado para auditoría"));

        AuditLog log = new AuditLog();
        log.setAction(action);
        log.setDescription("El usuario " + user.getName() + " (ID: " + user.getId() + ") hizo la acción: " + action + ". Detalles: " + details);
        log.setActionDate(LocalDateTime.now());
        log.setUser(user);
        log.setOrganization(user.getOrganization());

        auditLogRepository.save(log);
    }

    public List<AuditLog> getLogsByOrganization(Long organizationId) {
        return auditLogRepository.findByOrganizationIdOrderByActionDateDesc(organizationId);
    }
}