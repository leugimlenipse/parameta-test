package com.parameta.soapservice.service.impl;

import com.parameta.soapservice.compiled.employee.EmployeeRequest;
import com.parameta.soapservice.mapper.IEmployeeModelMapper;
import com.parameta.soapservice.model.EmployeeModel;
import com.parameta.soapservice.repository.IDocumentTypeRepository;
import com.parameta.soapservice.repository.IEmployeeRepository;
import com.parameta.soapservice.repository.IPositionRepository;
import com.parameta.soapservice.service.interfaces.IEmployeeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements IEmployeeService {

    private final IEmployeeRepository employeeRepository;
    private final IDocumentTypeRepository documentTypeRepository;
    private final IPositionRepository positionRepository;

    private final IEmployeeModelMapper employeeMapper;

    @Override
    @Transactional
    public EmployeeModel registerEmployee(EmployeeRequest employeeRequest) {

        var documentType = documentTypeRepository.findByCode(employeeRequest.getEmployee().getDocumentType().toUpperCase());
        var position = positionRepository.findByName(employeeRequest.getEmployee().getPosition().toUpperCase());

        if (documentType.isPresent() && position.isPresent()) {
            EmployeeModel employeeModel = this.employeeMapper.toEmployeeModel(employeeRequest.getEmployee());
            employeeModel.setDocumentType(documentType.get().getId());
            employeeModel.setPositionId(position.get().getId());

            employeeModel = this.employeeRepository.save(employeeModel);
            return employeeModel;
        }
        return null;

    }


}
