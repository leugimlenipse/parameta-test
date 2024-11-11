package com.parameta.restservice.controller;

import com.parameta.restservice.controller.validation.ErrorDTO;
import com.parameta.restservice.dto.EmployeeDTO;
import com.parameta.restservice.service.interfaces.IEmployeeServie;
import com.parameta.restservice.util.IConstants;
import com.parameta.restservice.util.exception.ValidationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.GroupSequence;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
@Validated
public class EmployeeController {

    private final IEmployeeServie employeeService;

    @Operation(summary = "Register a new employee",
            description = "This endpoint allows you to register a new employee given a valid EmployeeDTO",
            tags = {"Employee"},
            method = "POST",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeDTO.class)),
                    description = "EmployeeDTO object that needs to be registered", required = true),
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Employee registered successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EmployeeDTO.class)
                            )
                    ),
                    @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
            })
    @PostMapping("/register")
    public ResponseEntity<EmployeeDTO> registerEmployee(@RequestBody @Valid EmployeeDTO employeeDTO) {

        var response = this.employeeService.registerEmployee(employeeDTO);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Register a new employee",
            description = "This endpoint allows you to register a new employee given the employee's information",
            tags = {"Employee"},
            method = "GET",
            parameters = {
                    @Parameter(
                            name = "names", description = "Employee's names", required = true,
                            in = ParameterIn.QUERY, schema = @Schema(type = "string")
                    ),
                    @Parameter(
                            name = "lastNames", description = "Employee's last names", required = true,
                            in = ParameterIn.QUERY, schema = @Schema(type = "string")
                    ),
                    @Parameter(
                            name = "documentType", description = "Employee's document type", required = true,
                            in = ParameterIn.QUERY, schema = @Schema(type = "string")
                    ),
                    @Parameter(
                            name = "documentNumber", description = "Employee's document number", required = true,
                            in = ParameterIn.QUERY, schema = @Schema(type = "string")
                    ),
                    @Parameter(
                            name = "birthDate", description = "Employee's birth date", required = true,
                            in = ParameterIn.QUERY, schema = @Schema(type = "string", format = "date", pattern = IConstants.DATE_FORMAT)
                    ),
                    @Parameter(
                            name = "hiringDate", description = "Employee's hiring date", required = true,
                            in = ParameterIn.QUERY, schema = @Schema(type = "string", format = "date", pattern = IConstants.DATE_FORMAT)
                    ),
                    @Parameter(
                            name = "position", description = "Employee's position", required = true,
                            in = ParameterIn.QUERY, schema = @Schema(type = "string")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Employee registered successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EmployeeDTO.class)
                            )
                    ),
                    @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))

            })
    @GetMapping("/register")
    public ResponseEntity<EmployeeDTO> registerEmployee(@RequestParam(value = "names") @Valid @NotBlank(message = "Los nombres son obligatorios") String names,
                                                        @RequestParam(value = "lastNames") @Valid @NotBlank(message = "Los apellidos son obligatorios") String lastNames,
                                                        @RequestParam(value = "documentType") @Valid @NotBlank(message = "El tipo de documento es obligatorio") String documentType,
                                                        @RequestParam(value = "documentNumber") @Valid @NotBlank(message = "El número de documento es obligatorio") String documentNumber,
                                                        @RequestParam(value = "birthDate") @DateTimeFormat(pattern = IConstants.DATE_FORMAT) @Valid @NotNull(message = "La fecha de nacimiento es obligatoria") @Past(message = "La fecha de nacimiento debe ser anterior a la fecha actual") Date birthDate,
                                                        @RequestParam(value = "hiringDate") @DateTimeFormat(pattern = IConstants.DATE_FORMAT) @Valid @NotNull(message = "La fecha de contratación es obligatoria") @PastOrPresent(message = "La fecha de contratación debe ser anterior o igual a la fecha actual") Date hiringDate,
                                                        @RequestParam(value = "position") @Valid @NotBlank(message = "El cargo es obligatorio") String position,
                                                        @RequestParam(value = "salary") @Valid @NotNull(message = "El salario es obligatorio") Double salary
                                                        ) {
        if (hiringDate.before(birthDate)) throw new ValidationException("La fecha de nacimiento debe ser anterior a la fecha de contratación");
        var employeeDTO = EmployeeDTO.builder()
                .names(names)
                .lastNames(lastNames)
                .documentType(documentType)
                .documentNumber(documentNumber)
                .birthDate(birthDate)
                .hiringDate(hiringDate)
                .position(position)
                .salary(salary)
                .build();

        var response = this.employeeService.registerEmployee(employeeDTO);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.badRequest().build();
    }
}
