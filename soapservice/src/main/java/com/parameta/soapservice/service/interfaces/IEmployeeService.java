package com.parameta.soapservice.service.interfaces;

import com.parameta.soapservice.compiled.employee.Employee;
import com.parameta.soapservice.compiled.employee.EmployeeRequest;
import com.parameta.soapservice.model.EmployeeModel;

public interface IEmployeeService {

    EmployeeModel registerEmployee(EmployeeRequest employeeRequest);
}
