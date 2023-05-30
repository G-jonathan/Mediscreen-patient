package com.openclassroomsProject.Mediscreenpatient.constants;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link ExceptionConstants} class.
 *
 * @author Jonathan GOUVEIA
 * @version 1.0
 */
class ExceptionConstantsTest {

    @Test
    public void testDefaultResourceNotFoundConstant() {
        String expectedMessage = "Resource not found";
        String actualMessage = ExceptionConstants.DEFAULT_RESOURCE_NOT_FOUND;
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testPatientNotFoundConstant() {
        String expectedMessage = "Could not find Patient with id: ";
        String actualMessage = ExceptionConstants.PATIENT_NOT_FOUND;
        Assertions.assertEquals(expectedMessage, actualMessage);
    }
}