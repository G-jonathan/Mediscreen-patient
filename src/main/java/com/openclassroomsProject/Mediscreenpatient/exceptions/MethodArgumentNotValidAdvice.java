package com.openclassroomsProject.Mediscreenpatient.exceptions;

import com.openclassroomsProject.Mediscreenpatient.constants.ValidationConstants;
import com.openclassroomsProject.Mediscreenpatient.exceptions.dto.ErrorDTO;
import com.openclassroomsProject.Mediscreenpatient.exceptions.dto.FieldErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.ArrayList;
import java.util.List;

/**
 * Manage behavior when MethodArgumentNotValidException is thrown
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@ControllerAdvice
public class MethodArgumentNotValidAdvice {

    /**
     * Build an error send as an HTTP response with a validation exception
     *
     * @param methodArgumentNotValidException The exception thrown
     * @return An errorDto object
     * @see ErrorDTO
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO processValidationError(MethodArgumentNotValidException methodArgumentNotValidException) {
        List<FieldError> bindingFieldErrors = methodArgumentNotValidException.getBindingResult().getFieldErrors();
        List<FieldErrorDTO> finalFieldErrors = new ArrayList<>();
        for (FieldError fieldError : bindingFieldErrors) {
            finalFieldErrors.add(new FieldErrorDTO(
                    fieldError.getObjectName(),
                    fieldError.getField(),
                    fieldError.getRejectedValue(),
                    fieldError.getDefaultMessage()));
        }
        return new ErrorDTO(HttpStatus.BAD_REQUEST.value(), ValidationConstants.VALIDATION_ERROR, finalFieldErrors);
    }
}