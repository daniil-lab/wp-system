package com.wp.system.other;

public class ValidationErrorMessages {
    public final static String NO_EMPTY = "check field exist and him value on null";
    public final static String INVALID_PASSWORD_LENGTH = "Invalid password length. Min 6, max 32.";
    public final static String PHONE_VALIDATION_FAILED = "phone number validation failed, check given phone";
    public final static String EMAIL_VALIDATION_FAILED = "email validation failed, check given email";
    public final static String PINCODE_VALIDATION_FAILED = "pincode validation failed, pincode max and min length 4";
    public final static String INVALID_HEX_CODE = "invalid hex code, need pattern \"#XXXXXX\" or \"#XXX\"";
    public final static String INVALID_CATEGORY_LIMIT = "invalid category limit, category limit can`t have negative value";
}
