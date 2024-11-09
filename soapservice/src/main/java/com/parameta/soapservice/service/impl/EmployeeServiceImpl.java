package com.parameta.soapservice.service.impl;

import com.parameta.soapservice.compiled.employee.EmployeeRequest;
import com.parameta.soapservice.model.EmployeeModel;
import com.parameta.soapservice.repository.IDocumentTypeRepository;
import com.parameta.soapservice.repository.IEmployeeRepository;
import com.parameta.soapservice.repository.IPositionRepository;
import com.parameta.soapservice.service.interfaces.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements IEmployeeService {

    private final IEmployeeRepository employeeRepository;
    private final IDocumentTypeRepository documentTypeRepository;
    private final IPositionRepository positionRepository;

    @Override
    public EmployeeModel registerEmployee(EmployeeRequest employeeRequest) {

        var documentType = documentTypeRepository.findByCode(employeeRequest.getEmployee().getDocumentType());
        var position = positionRepository.findByName(employeeRequest.getEmployee().getPosition());

        EmployeeModel employeeModel = new EmployeeModel();
        if (documentType.isPresent() && position.isPresent()) {
            employeeModel.setDocumentType(documentType.get().getId());
            employeeModel.setPositionId(position.get().getId());

            employeeModel.setDocumentNumber(employeeRequest.getEmployee().getDocumentNumber());
            employeeModel.setNames(employeeRequest.getEmployee().getNames());
            employeeModel.setLastNames(employeeRequest.getEmployee().getLastNames());
            employeeModel.setBirthDate(employeeRequest.getEmployee().getBirthDate().toGregorianCalendar().getTime());
            employeeModel.setHiringDate(employeeRequest.getEmployee().getHiringDate().toGregorianCalendar().getTime());
            employeeModel.setSalary(employeeRequest.getEmployee().getSalary());
        }

        employeeModel = this.employeeRepository.save(employeeModel);
        return employeeModel;
    }


}
