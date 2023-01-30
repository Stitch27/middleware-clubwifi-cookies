package com.totalplay.cookies.controller;

import com.totalplay.cookies.model.Result;
import org.springframework.http.HttpStatus;
import javax.servlet.http.HttpServletRequest;
import com.totalplay.cookies.model.Information;
import com.totalplay.cookies.model.ResponseGet;
import org.springframework.http.ResponseEntity;
import com.totalplay.cookies.model.ResponsePost;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.http.converter.HttpMessageNotReadableException;

@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponsePost> readableException(HttpMessageNotReadableException exception, HttpServletRequest request) {

        Result result = new Result();
        ResponsePost responsePost = new ResponsePost();

        result.setCode("-50");
        result.setDescription("Solicitud incorrecta.");
        responsePost.setResult(result);

        return new ResponseEntity(responsePost, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResponseGet> supportedException(HttpRequestMethodNotSupportedException exception, HttpServletRequest request) {

        Result result = new Result();
        Information information = new Information();
        ResponseGet responseGet = new ResponseGet();

        result.setCode("200");
        result.setDescription("Ingresar mac.");
        responseGet.setResult(result);
        responseGet.setInformation(information);

        return new ResponseEntity(responseGet, HttpStatus.BAD_REQUEST);
    }

}
