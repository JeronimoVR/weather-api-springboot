package com.jeronimo.weatherapi.exception;

import com.jeronimo.weatherapi.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntime(RuntimeException e) {
        ErrorResponse error = new ErrorResponse(e.getMessage(), 500);
        return ResponseEntity.status(500).body(error);
    }
}
