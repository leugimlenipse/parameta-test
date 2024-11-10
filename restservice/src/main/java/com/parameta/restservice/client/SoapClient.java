package com.parameta.restservice.client;

import com.parameta.restservice.client.generated.EmployeeRequest;
import com.parameta.restservice.client.generated.EmployeeResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

@Service
@PropertySource("classpath:application.properties")
public class SoapClient extends WebServiceGatewaySupport {


    public SoapClient(@Value("${soap-service.url}/ws") String SERVICE_URL) {
        setDefaultUri(SERVICE_URL);

        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.parameta.restservice.client.generated");
        setMarshaller(marshaller);
        setUnmarshaller(marshaller);
    }

    public EmployeeResponse registerEmployee(EmployeeRequest request) {
        EmployeeResponse response = (EmployeeResponse) getWebServiceTemplate().marshalSendAndReceive(request);
        return response;
    }
}
