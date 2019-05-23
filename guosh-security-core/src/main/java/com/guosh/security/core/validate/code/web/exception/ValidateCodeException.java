package com.guosh.security.core.validate.code.web.exception;

import org.springframework.security.core.AuthenticationException;

public class ValidateCodeException extends AuthenticationException {


    public ValidateCodeException(String msg) {
        super(msg);
    }
}
