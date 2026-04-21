package repository;
import model.Documento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DocumentoRepository  extends JpaRepository<Documento, Long> {
    List<Documento> findByEstado(String estado);

    @Query(value = "SELECT * FROM documento WHERE autor = ?1", nativeQuery = true)
    List<Documento> buscarPorAutor(String autor);
}
