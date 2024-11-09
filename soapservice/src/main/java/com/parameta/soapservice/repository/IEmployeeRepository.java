package com.parameta.soapservice.repository;

import com.parameta.soapservice.model.EmployeeModel;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface IEmployeeRepository extends JpaRepository<EmployeeModel, Integer> {

    EmployeeModel findByNames(String nombre);
}
