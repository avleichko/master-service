package com.adidas.masterservice.app.exceptions;

import com.adidas.masterservice.app.dto.ErrorDto;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
@Slf4j
public class ErrorControllerAdvice {

    private final MeterRegistry meterRegistry;

    @Autowired
    public ErrorControllerAdvice(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @ExceptionHandler({ CommonMasterServiceException.class})                                                               // 400 HTTP code
    public ResponseEntity<ErrorDto> handleRuntimeException(Exception e) {
        meterRegistry.counter("critical-errors", Tags.of("exception details", e.getMessage())).increment(1);
        final ErrorDto errorDto = new ErrorDto(new Date(), e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }
}
