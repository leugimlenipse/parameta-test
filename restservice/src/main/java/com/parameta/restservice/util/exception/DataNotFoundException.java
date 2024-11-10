package com.parameta.restservice.util.exception;

import java.io.Serial;

public class DataNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 6000126363445519660L;

    public DataNotFoundException(String message) {
        super(message);
    }
}
