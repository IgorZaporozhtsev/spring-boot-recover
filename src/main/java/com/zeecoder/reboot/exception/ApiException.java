package com.zeecoder.reboot.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
public class ApiException {

    @ToString
    @Getter
    enum Code{
        USER_NOT_FOUND_EXCEPTION("User is not present", HttpStatus.NOT_FOUND,  ZonedDateTime.now(ZoneId.of("Z")));

        private final String message;
        private final HttpStatus httpStatus;
        private final ZonedDateTime timestamp;

        Code(String message, HttpStatus httpStatus, ZonedDateTime timestamp) {
            this.message = message;
            this.httpStatus = httpStatus;
            this.timestamp = timestamp;
        }
    }

    private final String message;
    private final HttpStatus status;
    private final ZonedDateTime timestamp;

    ApiException(String message, HttpStatus status, ZonedDateTime timestamp) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
    }
}
