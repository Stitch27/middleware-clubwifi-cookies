package com.totalplay.cookies.controller;

import com.totalplay.cookies.model.Result;
import org.springframework.http.HttpStatus;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import com.totalplay.cookies.model.ResponsePost;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
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

}
