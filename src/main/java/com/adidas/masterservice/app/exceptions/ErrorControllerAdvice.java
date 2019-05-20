package com.adidas.masterservice.app.exceptions;

import com.adidas.masterservice.app.dto.ErrorDto;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Date;

@ControllerAdvice
@Slf4j
public class ErrorControllerAdvice {

    private final MeterRegistry meterRegistry;

    @Autowired
    public ErrorControllerAdvice(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @ExceptionHandler({ NotImplementedException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto handleRuntimeException(Exception e) {
        meterRegistry.counter("critical-errors", Tags.of("exception details", e.getMessage()));
        return new ErrorDto(new Date(), e.getLocalizedMessage());
    }
}
