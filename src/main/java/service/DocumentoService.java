package service;
import model.Documento;
import org.springframework.stereotype.Service;
import repository.DocumentoRepository;

import java.util.List;

@Service
public class DocumentoService {

    private final DocumentoRepository repo;

    public DocumentoService(DocumentoRepository repo) {
        this.repo = repo;
    }

    public Documento guardar(Documento d) {
        return repo.save(d);
    }

    public List<Documento> listar() {
        return repo.findAll();
    }

    public List<Documento> porEstado(String estado) {
        return repo.findByEstado(estado);
    }
}
