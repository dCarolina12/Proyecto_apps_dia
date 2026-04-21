package controller;
import model.Documento;
import org.springframework.web.bind.annotation.*;
import service.DocumentoService;

import java.util.List;

@RestController
@RequestMapping("/documentos")
public class DocumentoController {
    
    private final DocumentoService service;

    public DocumentoController(DocumentoService service) {
        this.service = service;
    }

    @PostMapping
    public Documento crear(@RequestBody Documento d) {
        return service.guardar(d);
    }

    @GetMapping
    public List<Documento> listar() {
        return service.listar();
    }
}
