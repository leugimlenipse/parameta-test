package com.parameta.restservice.service.impl;

import com.parameta.restservice.client.SoapClient;
import com.parameta.restservice.client.generated.Employee;
import com.parameta.restservice.client.generated.EmployeeRequest;
import com.parameta.restservice.client.generated.EmployeeResponse;
import com.parameta.restservice.dto.EmployeeDTO;
import com.parameta.restservice.mapper.IEmployeeMapper;
import com.parameta.restservice.model.EmployeeModel;
import com.parameta.restservice.repository.IDocumentTypeRepository;
import com.parameta.restservice.repository.IEmployeeRepository;
import com.parameta.restservice.repository.IPositionRepository;
import com.parameta.restservice.service.interfaces.IEmployeeServie;
import com.parameta.restservice.util.exception.DataNotFoundException;
import com.parameta.restservice.util.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements IEmployeeServie {

    private final IDocumentTypeRepository documentTypeRepository;
    private final IPositionRepository positionRepository;
    private final IEmployeeRepository employeeRepository;

    private final IEmployeeMapper employeeMapper;

    private final SoapClient soapClient;

    @Override
    public EmployeeDTO registerEmployee(EmployeeDTO dto) {

        if (getTimeDiference(dto.getBirthDate()).getYears() < 18)
            throw new ValidationException("Employee must be at least 18 years old");

        var position = positionRepository.findByName(dto.getPosition().toUpperCase()).orElseThrow(() -> new DataNotFoundException("Position not found"));
        var document = documentTypeRepository.findByCode(dto.getDocumentType().toUpperCase()).orElseThrow(() -> new DataNotFoundException("Document type not found"));

        List<EmployeeModel> registeredEmployes = this.employeeRepository.findAllByDocumentNumberAndDocumentType(dto.getDocumentNumber(), document.getId());
        if (!registeredEmployes.isEmpty()) throw new ValidationException("Employee with document type and number already registered");

        Employee employee = this.employeeMapper.toEmployee(dto);
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setEmployee(employee);

        var response = soapClient.registerEmployee(employeeRequest);
        if (response != null) {
            dto.setId(response.getEmployeeId());
            calculateEmployeeDates(dto);
            return dto;
        }
        return null;
    }

    private void calculateEmployeeDates(EmployeeDTO dto) {
        Period age = getTimeDiference(dto.getBirthDate());
        Period employmentTime = getTimeDiference(dto.getHiringDate());
        dto.setEmployeeAge(age.getYears() + " years, " + age.getMonths() + " months and " + age.getDays() + " days");
        dto.setEmploymentTime(employmentTime.getYears() + " years, " + employmentTime.getMonths() + " months and " + employmentTime.getDays() + " days");
    }

    private Period getTimeDiference(Date initialDate) {
        Calendar initial = Calendar.getInstance();
        initial.setTime(initialDate);

        LocalDate startDate = LocalDate.of(initial.get(Calendar.YEAR), initial.get(Calendar.MONTH)+1, initial.get(Calendar.DAY_OF_MONTH));

        return Period.between(startDate, LocalDate.now());
    }
}
