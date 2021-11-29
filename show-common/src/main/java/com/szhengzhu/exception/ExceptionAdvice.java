package com.szhengzhu.exception;

import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.net.BindException;

/**
 * @author Jehon Zeng
 */
@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(value = BindException.class)
    public Result defaultErrorHandler(HttpServletRequest req, HttpServletResponse response, Exception e) {
        log.error(e.getMessage());
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new Result(StatusCode._5000);
    }

    /**
     * 方法参数校验
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        return new Result(StatusCode._4004);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Result handleConstraintViolationException(ConstraintViolationException e) {
        log.error(e.getMessage());
        return new Result(StatusCode._4004);
    }

    @ExceptionHandler(ShowException.class)
    public Result handleShowException(ShowException e) {
        log.error(e.getMessage());
        return new Result(e.getCode(), e.getMessage());
    }
}
