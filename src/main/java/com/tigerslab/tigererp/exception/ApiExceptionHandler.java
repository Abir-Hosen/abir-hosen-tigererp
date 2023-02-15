package com.tigerslab.tigererp.exception;

import com.tigerslab.tigererp.exception.ApiErrorSqlIntegrityConstraintViolation;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApiExceptionHandler {
	
	public ApiExceptionHandler() {
	}
	
    @ExceptionHandler({SQLException.class,DataAccessException.class,ConstraintViolationException.class})
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ApiErrorSqlIntegrityConstraintViolation dbConstraint(DataAccessException ex) {
    	String errorMsg = ex.getMessage();
		
    	return new ApiErrorSqlIntegrityConstraintViolation("DB_CONSTRAINT_FAILD", errorMsg);
	}

}
