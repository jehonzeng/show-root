package com.szhengzhu.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(value = Exception.class)
    public Result<String> defaultErrorHandler(HttpServletRequest req, HttpServletResponse response, Exception e) {
        e.printStackTrace(); 
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        // result.setData(e.getMessage());
        return new Result<String>(StatusCode._5000);
    }
}
