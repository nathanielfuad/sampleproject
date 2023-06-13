package com.obs.sampleproject.model.exception;

import lombok.Getter;

@Getter
public class GeneralErrorException extends RuntimeException{
    private final String errorCode;

    public GeneralErrorException(String errorCode){
        this.errorCode = errorCode;
    }

}
