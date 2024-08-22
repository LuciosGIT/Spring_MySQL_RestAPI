package com.project.springmysql.springmysqlproject.dto;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


 

@JsonPropertyOrder({"id", "name", "email", "phoneNumber"})
public class PersonDTO extends RepresentationModel<PersonDTO> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("id")
	private Long key;
	private String name;
	private String email;
	private String phoneNumber;
	private Boolean enabled;
	
	public PersonDTO() {
		
	}

	public PersonDTO(Long key, String name, String email, String phoneNumber) {
		super();
		this.key = key;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}


	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		PersonDTO personDTO = (PersonDTO) o;
		return Objects.equals(key, personDTO.key) && Objects.equals(name, personDTO.name) && Objects.equals(email, personDTO.email) && Objects.equals(phoneNumber, personDTO.phoneNumber) && Objects.equals(enabled, personDTO.enabled);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), key, name, email, phoneNumber, enabled);
	}
}
