package com.openclassroomsProject.Mediscreenpatient.exceptions;

import com.openclassroomsProject.Mediscreenpatient.constants.ExceptionConstants;

/**
 * Custom exception when a Patient resource is not found
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
public class PatientNotFoundException extends RuntimeException {

    /**
     * Exception used to indicate when a Patient is wanted but not found.
     *
     * @param id Identifier of the wanted patient.
     */
    public PatientNotFoundException(int id) {
        super(ExceptionConstants.PATIENT_NOT_FOUND + id);
    }
}