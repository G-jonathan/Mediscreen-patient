package com.openclassroomsProject.Mediscreenpatient.constants;

/**
 * Constants used for validation of object received by the controller.
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
public class ValidationConstants {
    public static final String NOT_NULL = "Can't be null";
    public static final String WRONG_FORMAT = "Wrong format";
    public static final String REGEX_PHONE_NUMBER = "^[0-9]{3}?-?[0-9]{3}-?[0-9]{4}$|^$";
    public static final String REGEX_DATE_OF_BIRTH = "^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$";
    public static final String VALIDATION_ERROR = "Validation error";
}