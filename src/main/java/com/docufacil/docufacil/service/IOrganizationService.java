package com.docufacil.docufacil.service;

import com.docufacil.docufacil.dto.OrganizationDTO;
import java.util.List;

public interface IOrganizationService {
    List<OrganizationDTO> listarTodas();
    OrganizationDTO buscarPorId(Long id);
    OrganizationDTO guardar(OrganizationDTO dto);
    void eliminar(Long id);
}
