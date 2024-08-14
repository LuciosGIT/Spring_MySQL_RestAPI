package com.project.springmysql.springmysqlproject.exceptionhandlercontroller;

import java.time.Instant;

import com.project.springmysql.springmysqlproject.exceptions.InvalidJwtAuthenticationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.project.springmysql.springmysqlproject.exceptions.ObjectNotFoundException;
import com.project.springmysql.springmysqlproject.exceptions.RequiredObjectIsNullException;
import com.project.springmysql.springmysqlproject.exceptions.StandardError;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFoundHandler(ObjectNotFoundException e, HttpServletRequest request){
		StandardError error = new StandardError();
		error.setTimeStamp(Instant.now());
		error.setStatus(HttpStatus.NOT_FOUND);
		error.setError(e.getMessage());
		error.setPath(request.getRequestURI());
		return ResponseEntity.status(error.getStatus()).body(error);
	}
	 
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandardError> emailOrPhoneExistsHandler(DataIntegrityViolationException e, HttpServletRequest request){
		StandardError error = new StandardError();
		error.setTimeStamp(Instant.now());
		error.setStatus(HttpStatus.BAD_REQUEST);
		error.setError(e.getMessage());
		error.setPath(request.getRequestURI());
		return ResponseEntity.status(error.getStatus()).body(error);
	}
	
	@ExceptionHandler(RequiredObjectIsNullException.class)
	public ResponseEntity<StandardError> nullObjectFoundHandler(RequiredObjectIsNullException e , HttpServletRequest request){
		StandardError error = new StandardError();
		error.setTimeStamp(Instant.now());
		error.setStatus(HttpStatus.BAD_REQUEST);
		error.setError(e.getMessage());
		error.setPath(request.getRequestURI());
		return ResponseEntity.status(error.getStatus()).body(error);
		
		
	}

	@ExceptionHandler(InvalidJwtAuthenticationException.class)
	public ResponseEntity<StandardError> handleInvalidJwtAuthentcationException(RequiredObjectIsNullException e , HttpServletRequest request){
		StandardError error = new StandardError();
		error.setTimeStamp(Instant.now());
		error.setStatus(HttpStatus.FORBIDDEN);
		error.setError(e.getMessage());
		error.setPath(request.getRequestURI());
		return ResponseEntity.status(error.getStatus()).body(error);


	}
}
