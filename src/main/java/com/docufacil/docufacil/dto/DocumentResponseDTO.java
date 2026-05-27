package com.docufacil.docufacil.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DocumentResponseDTO {
    private Long id;
    private String title;
    private String fileName;
    private String fileType;
    private String status;
    private LocalDateTime uploadDate;
    private String userName;
    private Long organizationId;      
}