package com.parameta.soapservice.endpoint;

import com.parameta.soapservice.compiled.employee.EmployeeRequest;
import com.parameta.soapservice.compiled.employee.EmployeeResponse;
import com.parameta.soapservice.model.EmployeeModel;
import com.parameta.soapservice.service.interfaces.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
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
        EmployeeResponse response = new EmployeeResponse();
//
//        EmployeeModel employeeModel = new EmployeeModel();
//        BeanUtils.copyProperties(request.getEmployee(), employeeModel);

        var employeeModel = this.employeeService.registerEmployee(request);
        response.setEmployeeId(employeeModel.getId());
        return response;
    }
}
