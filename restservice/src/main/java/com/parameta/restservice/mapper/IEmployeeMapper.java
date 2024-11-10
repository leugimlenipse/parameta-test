package com.parameta.restservice.mapper;

import com.parameta.restservice.client.generated.Employee;
import com.parameta.restservice.dto.EmployeeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IEmployeeMapper {

    Employee toEmployee(EmployeeDTO employeeDTO);
}
