package com.parameta.soapservice.mapper;

import com.parameta.soapservice.compiled.employee.Employee;
import com.parameta.soapservice.model.EmployeeModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IEmployeeModelMapper {

    @Mapping(target = "birthDate", expression = "java(employee.getBirthDate().toGregorianCalendar().getTime())")
    @Mapping(target = "hiringDate", expression = "java(employee.getHiringDate().toGregorianCalendar().getTime())")
    @Mapping(target = "position", ignore = true)
    @Mapping(target = "documentType", ignore = true)
    EmployeeModel toEmployeeModel(Employee employee);
}
