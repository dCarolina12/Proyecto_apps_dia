package com.docufacil.docufacil.service;

import com.docufacil.docufacil.dto.OrganizationDTO;
import com.docufacil.docufacil.mapper.OrganizationMapper;
import com.docufacil.docufacil.model.Organization;
import com.docufacil.docufacil.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganizationService implements IOrganizationService {

    @Autowired
    private OrganizationRepository repository;

    @Autowired
    private OrganizationMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<OrganizationDTO> listarTodas() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrganizationDTO guardar(OrganizationDTO dto) {

        if (dto.getName() == null || dto.getName().isEmpty()) {
            throw new RuntimeException("El nombre de la organización es obligatorio");
        }

        Organization entity = mapper.toEntity(dto);
        Organization saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public OrganizationDTO buscarPorId(Long id) {
        Organization org = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organización no encontrada"));
        return mapper.toDto(org);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}