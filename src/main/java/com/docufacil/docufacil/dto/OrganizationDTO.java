package com.docufacil.docufacil.dto;

import lombok.Data;

@Data
public class OrganizationDTO {
    private Long id;
    private String name;
    private String nit;
    private boolean active;
}
