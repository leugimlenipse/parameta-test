package com.parameta.restservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.parameta.restservice.util.IConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.GroupSequence;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@GroupSequence({EmployeeDTO.class, IConstants.DateValidation.class})
@Schema(name = "EmployeeDTO", description = "Employee data transfer object used to register and get basic employee information")
public class EmployeeDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -2103223120436377869L;

//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(description = "Employee identifier", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Valid
    @NotBlank(message = "Los nombres son obligatorios")
    @Schema(description = "Employee names", type = "string", example = "John")
    private String names;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Schema(description = "Employee last names", example = "Doe")
    private String lastNames;

    @Valid
    @NotBlank(message = "El tipo de documento es obligatorio")
    @Schema(description = "Document type", example = "CC")
    private String documentType;

    @NotBlank(message = "El número de documento es obligatorio")
    @Schema(description = "Document number", example = "1234567890")
    private String documentNumber;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser anterior a la fecha actual")
    @JsonFormat(pattern = IConstants.DATE_FORMAT, timezone = IConstants.DATE_TIMEZONE)
    @Schema(description = "Employee birth date", example = "1990-01-01")
    private Date birthDate;

    @NotNull(message = "La fecha de contratación es obligatoria")
    @PastOrPresent(message = "La fecha de contratación debe ser anterior o igual a la fecha actual")
    @JsonFormat(pattern = IConstants.DATE_FORMAT, timezone = IConstants.DATE_TIMEZONE)
    @Schema(description = "Employee hiring date", example = "2021-01-01")
    private Date hiringDate;

    @NotBlank(message = "El cargo es obligatorio")
    @Schema(description = "Employee position", example = "DEVELOPER")
    private String position;

    @NotNull(message = "El salario es obligatorio")
    @DecimalMin(value = "0.0", message = "El salario debe ser mayor o igual a 0")
    @Schema(description = "Employee salary", example = "6.000.000")
    private Double salary;

    @Schema(description = "Employee employment time", example = "2 years, 3 months and 4 days", accessMode = Schema.AccessMode.READ_ONLY)
    private String employmentTime;

    @Schema(description = "Employee age", example = "30 years, 5 months and 10 days", accessMode = Schema.AccessMode.READ_ONLY)
    private String employeeAge;

    @AssertTrue(message = "La fecha de nacimiento debe ser anterior a la fecha de contratación", groups = {IConstants.DateValidation.class})
    @JsonIgnore
    public boolean isBirthDateBeforeHiringDate() {
        return birthDate.before(hiringDate);
    }
}
