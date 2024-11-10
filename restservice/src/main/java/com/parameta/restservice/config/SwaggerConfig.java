package com.parameta.restservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("localhost:8080${server.servlet.context-path}")
    String SERVER_URL;

    @Bean
    public OpenAPI springOpenApi() {

        Server server = new Server()
                .url(SERVER_URL)
                .description("Development server");

        Contact author = new Contact();
        author.setName("Miguel Espinel");
        author.setEmail("miguele2141@gmail.com");

        Info information = new Info()
                .title("Parameta test - Employee API")
                .description("Main API to register employees. This API uses a SOAP service to manage employees information.")
                .version("1.0")
                .contact(author);

        return new OpenAPI()
                .servers(List.of(server))
                .info(information);
    }
}
