package com.parameta.soapservice.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfiguration extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messsageDispatcherServlet(ApplicationContext context) {
        MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
        messageDispatcherServlet.setApplicationContext(context);
        messageDispatcherServlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(messageDispatcherServlet, "/ws/*");
    }

    @Bean(name = "employee")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema employeeSchema) {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setTargetNamespace("http://parameta.com/soapservice/compiled/employee");
        definition.setPortTypeName("EmployeePort");
        definition.setLocationUri("/ws");
        definition.setSchema(employeeSchema);
        return definition;
    }

    @Bean
    public XsdSchema employeeSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/employee.xsd"));
    }
}
