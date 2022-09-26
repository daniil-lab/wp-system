package com.wp.system.utils.sber;

public enum SberRegisterState {
    WAIT_REGISTER,
    REGISTER,
    WAIT_SUBMIT,
    SUBMIT,
    WAIT_CREATE_PIN,
    CREATE_PIN,
    ERROR,
    FINISHED
}
