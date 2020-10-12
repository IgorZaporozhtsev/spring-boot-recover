package com.zeecoder.reboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.zeecoder.reboot.exception.ApiException.Code.USER_NOT_FOUND_EXCEPTION;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(){

        ApiException apiException = new ApiException(
            USER_NOT_FOUND_EXCEPTION.getMessage(),
            USER_NOT_FOUND_EXCEPTION.getHttpStatus(),
            USER_NOT_FOUND_EXCEPTION.getTimestamp());

        return new ResponseEntity<Object>(apiException, HttpStatus.BAD_REQUEST);
    }
}
