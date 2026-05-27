package com.docufacil.docufacil.controller;

import com.docufacil.docufacil.dto.OrganizationDTO;
import com.docufacil.docufacil.service.IOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200") 
@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {

    @Autowired
    private IOrganizationService service;

    @GetMapping
    public ResponseEntity<List<OrganizationDTO>> listar() {
        return new ResponseEntity<>(service.listarTodas(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrganizationDTO> guardar(@RequestBody OrganizationDTO dto) {
        return new ResponseEntity<>(service.guardar(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationDTO> obtenerPorId(@PathVariable("id") Long id) {
        return new ResponseEntity<>(service.buscarPorId(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id) {
        service.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}