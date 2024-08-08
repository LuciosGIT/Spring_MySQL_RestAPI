package com.project.springmysql.springmysqlproject.dto;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

 


public class UserDTO extends RepresentationModel<UserDTO> implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long key;
	private String name;
	private String email;
	private String phoneNumber;
	
	public UserDTO() {
		
	}

	public UserDTO(Long key, String name, String email, String phoneNumber) {
		super();
		this.key = key;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}



	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public int hashCode() {
		return Objects.hash(key);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDTO other = (UserDTO) obj;
		return Objects.equals(key, other.key);
	}
	
	
}
