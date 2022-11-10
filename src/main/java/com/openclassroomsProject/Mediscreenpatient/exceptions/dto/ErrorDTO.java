package com.openclassroomsProject.Mediscreenpatient.exceptions.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Used to build a bad_request HTTP response when validation failed
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 * @see com.openclassroomsProject.Mediscreenpatient.exceptions.MethodArgumentNotValidAdvice
 */
public class ErrorDTO implements Serializable {
    private final int status;
    private final String message;
    private final List<FieldErrorDTO> fieldErrors;

    public ErrorDTO(int status, String message, List<FieldErrorDTO> fieldErrors) {
        this.status = status;
        this.message = message;
        this.fieldErrors = fieldErrors;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<FieldErrorDTO> getFieldErrors() {
        return fieldErrors;
    }
}