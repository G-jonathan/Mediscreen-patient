package com.openclassroomsProject.Mediscreenpatient.constants;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link ValidationConstants} class.
 *
 * @author Jonathan GOUVEIA
 * @version 1.0
 */
class ValidationConstantsTest {

    @Test
    public void testNotNullConstant() {
        Assertions.assertEquals("Can't be null", ValidationConstants.NOT_NULL);
    }

    @Test
    public void testWrongFormatConstant() {
        Assertions.assertEquals("Wrong format", ValidationConstants.WRONG_FORMAT);
    }

    @Test
    public void testRegexPhoneNumberConstant() {
        Assertions.assertEquals("^[0-9]{3}?-?[0-9]{3}-?[0-9]{4}$", ValidationConstants.REGEX_PHONE_NUMBER);
    }

    @Test
    public void testRegexDateOfBirthConstant() {
        Assertions.assertEquals("^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$", ValidationConstants.REGEX_DATE_OF_BIRTH);
    }

    @Test
    public void testValidationErrorConstant() {
        Assertions.assertEquals("Validation error", ValidationConstants.VALIDATION_ERROR);
    }
}