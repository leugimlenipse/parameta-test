package com.parameta.restservice.unit.service;

import com.parameta.restservice.client.SoapClient;
import com.parameta.restservice.client.generated.Employee;
import com.parameta.restservice.client.generated.EmployeeRequest;
import com.parameta.restservice.client.generated.EmployeeResponse;
import com.parameta.restservice.dto.EmployeeDTO;
import com.parameta.restservice.mapper.IEmployeeMapper;
import com.parameta.restservice.model.DocumentType;
import com.parameta.restservice.model.EmployeeModel;
import com.parameta.restservice.model.Position;
import com.parameta.restservice.repository.IDocumentTypeRepository;
import com.parameta.restservice.repository.IEmployeeRepository;
import com.parameta.restservice.repository.IPositionRepository;
import com.parameta.restservice.service.impl.EmployeeServiceImpl;
import com.parameta.restservice.util.exception.DataNotFoundException;
import com.parameta.restservice.util.exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private IDocumentTypeRepository documentTypeRepository;

    @Mock
    private IPositionRepository positionRepository;

    @Mock
    private IEmployeeRepository employeeRepository;

    @Mock
    private IEmployeeMapper employeeMapper;

    @Mock
    private SoapClient soapClient;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    void whenRegisteringEmployee_AndEmployeeUnderAge_ThrowsException() {
        // Given
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        EmployeeDTO dto = EmployeeDTO.builder().names("John").lastNames("Doe").documentType("CC").documentNumber("1234567890")
                .birthDate(calendar.getTime()).hiringDate(calendar.getTime()).build();

        // When
        // Then
        assertThrows(ValidationException.class, () -> employeeService.registerEmployee(dto));
    }

    @Test
    void whenRegisteringEmployee_AndInvalidDocumentType_ThrowsException() {
        // Given
        Calendar birthDate = Calendar.getInstance();
        birthDate.set(Calendar.YEAR, 1990);

        EmployeeDTO dto = EmployeeDTO.builder().names("John").lastNames("Doe").documentType("XX").position("XX").documentNumber("1234567890")
                .birthDate(birthDate.getTime()).hiringDate(new Date()).build();

        given(positionRepository.findByName(anyString())).willReturn(Optional.of(Position.builder().id(1).build()));
        given(documentTypeRepository.findByCode(anyString())).willReturn(Optional.empty());
        // When
        // Then
        assertThrows(DataNotFoundException.class, () -> employeeService.registerEmployee(dto));
    }

    @Test
    void whenRegisteringEmployee_AndInvalidPosition_ThrowsException() {
        // Given
        Calendar birthDate = Calendar.getInstance();
        birthDate.set(Calendar.YEAR, 1990);

        EmployeeDTO dto = EmployeeDTO.builder().names("John").lastNames("Doe").documentType("XX").position("XX").documentNumber("1234567890")
                .birthDate(birthDate.getTime()).hiringDate(new Date()).build();

        given(positionRepository.findByName(anyString())).willReturn(Optional.empty());
        // When
        // Then
        assertThrows(DataNotFoundException.class, () -> employeeService.registerEmployee(dto));
    }

    @Test
    void whenRegisteringEmployee_AndEmployeeAlreadyRegistered_ThrowsException() {
        // Given
        Calendar birthDate = Calendar.getInstance();
        birthDate.set(Calendar.YEAR, 1990);

        EmployeeDTO dto = EmployeeDTO.builder().names("John").lastNames("Doe").documentType("XX").position("XX").documentNumber("1234567890")
                .birthDate(birthDate.getTime()).hiringDate(new Date()).build();

        var doc = DocumentType.builder().id(1).build();
        var registeredEmployee = EmployeeModel.builder().id(1).build();
        given(positionRepository.findByName(anyString())).willReturn(Optional.of(Position.builder().id(1).build()));
        given(documentTypeRepository.findByCode(anyString())).willReturn(Optional.of(doc));
        given(employeeRepository.findAllByDocumentNumberAndDocumentType(dto.getDocumentNumber(), doc.getId())).willReturn(List.of(registeredEmployee));
        // When
        // Then
        assertThrows(ValidationException.class, () -> employeeService.registerEmployee(dto));
    }

    @Test
    void whenRegisteringEmployee_AndEmployeeRegisteredSuccessfully_ReturnsEmployee() throws DatatypeConfigurationException {
        // Given
        Calendar birthDate = Calendar.getInstance();
        Calendar hiringDate = Calendar.getInstance();
        birthDate.set(Calendar.YEAR, 1990);
        hiringDate.set(Calendar.YEAR, 2021);

        EmployeeDTO dto = EmployeeDTO.builder().names("John").lastNames("Doe").documentType("XX").position("XX").documentNumber("1234567890")
                .birthDate(birthDate.getTime()).hiringDate(hiringDate.getTime()).build();


        XMLGregorianCalendar birthDatecal = DatatypeFactory.newInstance().newXMLGregorianCalendar(LocalDate.ofInstant(birthDate.toInstant(), birthDate.getTimeZone().toZoneId()).toString());
        XMLGregorianCalendar hiringDatecal = DatatypeFactory.newInstance().newXMLGregorianCalendar(LocalDate.ofInstant(hiringDate.toInstant(), hiringDate.getTimeZone().toZoneId()).toString());

        Employee employee = new Employee();
        employee.setBirthDate(birthDatecal);
        employee.setHiringDate(hiringDatecal);
        employee.setDocumentType(dto.getDocumentType());
        employee.setPosition(dto.getPosition());

        EmployeeResponse response = new EmployeeResponse();
        response.setEmployeeId(1);

        var doc = DocumentType.builder().id(1).build();
        var position = Position.builder().id(1).build();
        given(positionRepository.findByName(anyString())).willReturn(Optional.of(position));
        given(documentTypeRepository.findByCode(anyString())).willReturn(Optional.of(doc));
        given(employeeRepository.findAllByDocumentNumberAndDocumentType(dto.getDocumentNumber(), doc.getId())).willReturn(List.of());
        given(employeeMapper.toEmployee(dto)).willReturn(employee);
        given(soapClient.registerEmployee(any())).willReturn(response);

        // When
        var responseDTO = employeeService.registerEmployee(dto);

        // Then
        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getId()).isNotNull().isEqualTo(1);
        assertThat(responseDTO.getEmployeeAge()).isNotNull().isNotEmpty();
        assertThat(responseDTO.getEmploymentTime()).isNotNull().isNotEmpty();

    }

}
