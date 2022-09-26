package com.wp.system.utils;

public class ValidationErrorMessages {
    public final static String NO_EMPTY = "check field exist and him value on null";
    public final static String INVALID_PASSWORD_LENGTH = "Invalid password length. Min 6, max 32.";
    public final static String PHONE_VALIDATION_FAILED = "phone number validation failed, check given phone";
    public final static String EMAIL_VALIDATION_FAILED = "email validation failed, check given email";
    public final static String PINCODE_VALIDATION_FAILED = "pincode validation failed, pincode max and min length 4";
    public final static String INVALID_HEX_CODE = "invalid hex code, need pattern \"#XXXXXX\" or \"#XXX\"";
    public final static String INVALID_CATEGORY_LIMIT = "invalid category limit, category limit can`t have negative value";
    public final static String NEGATIVE_CENTS = "cents can`t have negative value";
    public final static String NEGATIVE_AMOUNT = "amount can`t have negative value";
    public final static String MAXIMUM_CENTS = "cents can`t have value between 100";
    public final static String INVALID_BILL_NAME = "minimum bill name length 4. maximum 64";
    public final static String PLANNED_INCOME_NEGATIVE = "planned income can`t have negative value";
    public final static String INVALID_CATEGORY_NAME = "check category name length, minimum 4, maximum 64";
}
