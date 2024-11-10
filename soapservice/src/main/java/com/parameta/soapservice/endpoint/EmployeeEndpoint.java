package com.parameta.soapservice.endpoint;

import com.parameta.soapservice.compiled.employee.EmployeeRequest;
import com.parameta.soapservice.compiled.employee.EmployeeResponse;
import com.parameta.soapservice.service.interfaces.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@RequiredArgsConstructor
public class EmployeeEndpoint {

    private static final String NAMESPACE_URI = "http://parameta.com/soapservice/compiled/employee";

    private final IEmployeeService employeeService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "employeeRequest")
    @ResponsePayload
    public EmployeeResponse registerEmployee(@RequestPayload EmployeeRequest request) {
        var employeeModel = this.employeeService.registerEmployee(request);
        EmployeeResponse response = new EmployeeResponse();
        response.setEmployeeId(employeeModel.getId());
        return response;
    }
}
