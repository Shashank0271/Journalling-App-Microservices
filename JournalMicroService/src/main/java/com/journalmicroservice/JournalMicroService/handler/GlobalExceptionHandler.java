package com.journalmicroservice.JournalMicroService.handler;

import com.journalmicroservice.JournalMicroService.Exceptions.JournalEntryNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(JournalEntryNotFoundException.class)
    public ResponseEntity<Map<String, ?>> handleUserNotFoundException(JournalEntryNotFoundException entryNotFoundException) {
        logger.trace(entryNotFoundException.getMessage());
        return new ResponseEntity<>(
                new LinkedHashMap<>() {{
                    put("message", entryNotFoundException.getMessage());
                    put("success", false);
                    put("status", HttpStatus.NOT_FOUND);
                }},
                HttpStatus.NOT_FOUND
        );
    }

}
