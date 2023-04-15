package com.openclassroomsProject.Mediscreenpatient.exceptions;

import com.openclassroomsProject.Mediscreenpatient.constants.ExceptionConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Manage behavior when PatientNotFoundException is thrown
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@ControllerAdvice
public class PatientControllerAdvice extends ResponseEntityExceptionHandler {

    /**
     * Used to render an HTTP 404 and a body message when a PatientNotFoundException is thrown
     *
     * @param patientNotFoundException The exception thrown.
     * @return The body of the advice gives the message of the exception.
     */
    @ResponseBody
    @ExceptionHandler(PatientNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> PatientNotFoundHandler(PatientNotFoundException patientNotFoundException) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        if (patientNotFoundException.getMessage().isEmpty() || patientNotFoundException.getMessage().isBlank()) {
            body.put("message", ExceptionConstants.DEFAULT_RESOURCE_NOT_FOUND);
            return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
        }
        body.put("message", patientNotFoundException.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}