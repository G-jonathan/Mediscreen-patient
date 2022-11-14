package com.openclassroomsProject.Mediscreenpatient.exceptions;

import com.openclassroomsProject.Mediscreenpatient.constants.ExceptionConstants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Manage behavior when PatientNotFoundException is thrown
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@ControllerAdvice
public class PatientNotFoundAdvice {

    /**
     * Used to render an HTTP 404 and a body message when a PatientNotFoundException is thrown
     *
     * @param patientNotFoundException The exception thrown.
     * @return The body of the advice gives the message of the exception.
     */
    @ResponseBody
    @ExceptionHandler(PatientNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String PatientNotFoundHandler(PatientNotFoundException patientNotFoundException) {
        if (patientNotFoundException.getMessage().isEmpty() || patientNotFoundException.getMessage().isBlank()) {
            return ExceptionConstants.DEFAULT_RESOURCE_NOT_FOUND;
        }
        return patientNotFoundException.getMessage();
    }
}