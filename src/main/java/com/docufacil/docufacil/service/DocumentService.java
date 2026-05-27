package com.docufacil.docufacil.service;

import com.docufacil.docufacil.dto.DocumentResponseDTO;
import com.docufacil.docufacil.mapper.DocumentMapper;
import com.docufacil.docufacil.model.Document;
import com.docufacil.docufacil.model.User;
import com.docufacil.docufacil.repository.DocumentRepository;
import com.docufacil.docufacil.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private DocumentMapper documentMapper;

    public DocumentResponseDTO uploadDocument(String title, MultipartFile file, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + userId));

        String uniqueFileName = fileStorageService.storeFile(file);

        Document document = new Document();
        document.setTitle(title);
        document.setFileName(uniqueFileName);
        document.setFileType(file.getContentType());
        document.setUser(user);
        document.setOrganization(user.getOrganization()); 


        Document savedDocument = documentRepository.save(document);

        return documentMapper.toDTO(savedDocument);
    }

    public List<DocumentResponseDTO> getDocumentsByOrganization(Long organizationId) {
        return documentRepository.findByOrganizationId(organizationId)
                .stream()
                .map(documentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public byte[] downloadFile(Long documentId) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado con ID: " + documentId));

        return fileStorageService.loadFile(document.getFileName());
    }
    public void updateStatus(Long documentId, String newStatus) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado con ID: " + documentId));

        document.setStatus(newStatus);
        documentRepository.save(document);
    }
}