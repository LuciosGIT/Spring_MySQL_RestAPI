package com.project.springmysql.springmysqlproject.exceptions;




public class RequiredObjectIsNullException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public RequiredObjectIsNullException(String msg) {
		super(msg);
	}

}
