package com.docufacil.docufacil.repository;

import com.docufacil.docufacil.model.DocumentFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DocumentFlowRepository extends JpaRepository<DocumentFlow, Long> {
    List<DocumentFlow> findByDocumentIdOrderByActionDateAsc(Long documentId);
}