package com.project.springmysql.springmysqlproject.exceptions;

import java.io.Serializable;
import java.time.Instant;


import org.springframework.http.HttpStatus;

public class StandardError implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Instant timeStamp;
	private HttpStatus status;
	private String error;
	private String path;
	
	public StandardError() {
		
	}

	public StandardError(Instant timeStamp, HttpStatus status, String error, String path) {
		super();
		this.timeStamp = timeStamp;
		this.status = status;
		this.error = error;
		this.path = path;
	}

	public Instant getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Instant timeStamp) {
		this.timeStamp = timeStamp;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}	
}
