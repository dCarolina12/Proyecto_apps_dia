package com.docufacil.docufacil.mapper;

import com.docufacil.docufacil.dto.OrganizationDTO;
import com.docufacil.docufacil.model.Organization;
import org.springframework.stereotype.Component;

@Component
public class OrganizationMapper {

    public OrganizationDTO toDto(Organization organization) {
        OrganizationDTO dto = new OrganizationDTO();
        dto.setId(organization.getId());
        dto.setName(organization.getName());
        dto.setNit(organization.getNit());
        dto.setActive(organization.isActive());
        return dto;
    }

    public Organization toEntity(OrganizationDTO dto) {
        Organization entity = new Organization();
        entity.setName(dto.getName());
        entity.setNit(dto.getNit());
        entity.setActive(dto.isActive());
        return entity;
    }
}