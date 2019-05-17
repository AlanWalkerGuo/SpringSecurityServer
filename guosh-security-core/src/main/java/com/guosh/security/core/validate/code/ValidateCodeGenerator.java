package com.guosh.security.core.validate.code;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;



public interface ValidateCodeGenerator {
    ValidateCode generate(ServletWebRequest request);

}
