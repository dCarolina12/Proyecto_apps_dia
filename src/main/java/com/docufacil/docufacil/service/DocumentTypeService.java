package com.docufacil.docufacil.service;

import com.docufacil.docufacil.model.DocumentType;
import com.docufacil.docufacil.model.Organization;
import com.docufacil.docufacil.repository.DocumentTypeRepository;
import com.docufacil.docufacil.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentTypeService {

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    public DocumentType createDocumentType(String name, String description, Long organizationId) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new RuntimeException("Organización no encontrada"));

        DocumentType documentType = new DocumentType();
        documentType.setName(name);
        documentType.setDescription(description);
        documentType.setOrganization(organization);

        return documentTypeRepository.save(documentType);
    }

    public List<DocumentType> getTypesByOrganization(Long organizationId) {
        return documentTypeRepository.findByOrganizationId(organizationId);
    }
}