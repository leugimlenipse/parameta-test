package com.parameta.restservice.repository;

import com.parameta.restservice.client.generated.Employee;
import com.parameta.restservice.model.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEmployeeRepository extends JpaRepository<EmployeeModel, Integer> {

    List<EmployeeModel> findAllByDocumentNumberAndDocumentType(String documentNumber, Integer documentType);
}
