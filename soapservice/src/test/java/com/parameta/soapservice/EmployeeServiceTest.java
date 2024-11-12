package com.parameta.soapservice;

import com.parameta.soapservice.compiled.employee.Employee;
import com.parameta.soapservice.compiled.employee.EmployeeRequest;
import com.parameta.soapservice.mapper.IEmployeeModelMapper;
import com.parameta.soapservice.model.DocumentType;
import com.parameta.soapservice.model.EmployeeModel;
import com.parameta.soapservice.model.Position;
import com.parameta.soapservice.repository.IDocumentTypeRepository;
import com.parameta.soapservice.repository.IEmployeeRepository;
import com.parameta.soapservice.repository.IPositionRepository;
import com.parameta.soapservice.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private IEmployeeRepository employeeRepository;

    @Mock
    private IDocumentTypeRepository documentTypeRepository;

    @Mock
    private IPositionRepository positionRepository;

    @Mock
    private IEmployeeModelMapper employeeMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    void whenRegisteringEmployee_AndInformationValidated_ThenRegisterEmployee() {
        // Given
        Employee employee = new Employee();
        employee.setDocumentType("CC");
        employee.setPosition("Developer");
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setEmployee(employee);

        var documentType = DocumentType.builder().id(1).build();
        var position = Position.builder().id(1).build();
        var employeeModel = EmployeeModel.builder().id(1).build();
        given(documentTypeRepository.findByCode(any())).willReturn(Optional.of(documentType));
        given(positionRepository.findByName(any())).willReturn(Optional.of(position));
        given(employeeMapper.toEmployeeModel(any(Employee.class))).willReturn(employeeModel);
        given(employeeRepository.save(any(EmployeeModel.class))).willReturn(employeeModel);

        // When
        var result = employeeService.registerEmployee(employeeRequest);
        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(employeeModel.getId());
    }

    @Test
    void whenRegisteringEmployee_AndInformationInvalid_ReturnsNull() {
        // Given
        Employee employee = new Employee();
        employee.setDocumentType("CC");
        employee.setPosition("Developer");
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setEmployee(employee);

        given(documentTypeRepository.findByCode(any())).willReturn(Optional.empty());
        given(positionRepository.findByName(any())).willReturn(Optional.empty());

        // When
        var result = employeeService.registerEmployee(employeeRequest);

        // Then
        assertThat(result).isNull();
    }
}
