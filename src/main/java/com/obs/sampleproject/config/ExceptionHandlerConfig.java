package com.obs.sampleproject.config;

import com.obs.sampleproject.constants.ErrorCode;
import com.obs.sampleproject.model.exception.GeneralErrorException;
import com.obs.sampleproject.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerConfig extends ResponseEntityExceptionHandler {

    private final ResponseUtil responseUtil;

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Object>handleRuntimeException(RuntimeException exception){
        exception.printStackTrace();
        return responseUtil.generate(ErrorCode.GENERAL_ERROR, "Oops, Something wrong");
    }

    @ExceptionHandler(GeneralErrorException.class)
    protected ResponseEntity<Object>handleGeneralErrorException(GeneralErrorException generalErrorException){
        return responseUtil.generate(generalErrorException.getErrorCode(), "Oops, Something wrong");
    }
}
