package com.totalplay.cookies.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import org.springframework.http.HttpStatus;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;

@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> getException(HttpMessageNotReadableException exception, HttpServletRequest request) {

        HashMap<String, String> results = new LinkedHashMap<>();
        HashMap<String, Object> response = new LinkedHashMap<>();

        results.put("code", "-50");
        results.put("description", "Solicitud incorrecta.");

        response.put("results", results);

        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

}