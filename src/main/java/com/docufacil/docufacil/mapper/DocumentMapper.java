package com.docufacil.docufacil.mapper;

import com.docufacil.docufacil.dto.DocumentResponseDTO;
import com.docufacil.docufacil.model.Document;
import org.springframework.stereotype.Component;

@Component
public class DocumentMapper {

    public DocumentResponseDTO toDTO(Document document) {
        if (document == null) {
            return null;
        }

        DocumentResponseDTO dto = new DocumentResponseDTO();
        dto.setId(document.getId());
        dto.setTitle(document.getTitle());
        dto.setFileName(document.getFileName());
        dto.setFileType(document.getFileType());
        dto.setStatus(document.getStatus());
        dto.setUploadDate(document.getUploadDate());

        if (document.getUser() != null) {
            dto.setUserName(document.getUser().getName());
        }

        if (document.getOrganization() != null) {
            dto.setOrganizationId(document.getOrganization().getId());
        }

        return dto;
    }
}