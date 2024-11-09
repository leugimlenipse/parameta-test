package com.parameta.soapservice.repository;

import com.parameta.soapservice.model.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IDocumentTypeRepository extends JpaRepository<DocumentType, Integer> {

    Optional<DocumentType> findByCode(String type);
}
