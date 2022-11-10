package com.openclassroomsProject.Mediscreenpatient.exceptions.dto;

import org.springframework.lang.Nullable;
import java.io.Serializable;

/**
 * Used to build a bad_request HTTP response when validation failed
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 * @see com.openclassroomsProject.Mediscreenpatient.exceptions.MethodArgumentNotValidAdvice
 */
public class FieldErrorDTO implements Serializable {
    private final String objectName;
    private final String field;
    @Nullable
    private final Object rejectedValue;
    @Nullable
    private final String message;

    public FieldErrorDTO(String objectName, String field, Object rejectedValue, String message) {
        this.objectName = objectName;
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getField() {
        return field;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }

    public String getMessage() {
        return message;
    }
}