package com.parameta.restservice.controller.validation;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@Data
@Builder
public class ErrorDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -1183438562066207213L;

    private String path;
    private HttpStatus status;
    private Integer code;
    private LocalDateTime timestamp;
    private Map<String, String> errors;
}
