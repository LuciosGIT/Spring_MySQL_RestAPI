package com.project.springmysql.integrationtests.vo;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.Objects;


@XmlRootElement
public class PersonDTO  implements Serializable {
	private static final long serialVersionUID = 1L;



	private Long id;
	private String name;
	private String email;
	private String phoneNumber;
	private Boolean enabled;

	public PersonDTO() {
		
	}

	public PersonDTO(Long id, String name, String email, String phoneNumber) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PersonDTO personDTO = (PersonDTO) o;
		return Objects.equals(id, personDTO.id) && Objects.equals(name, personDTO.name) && Objects.equals(email, personDTO.email) && Objects.equals(phoneNumber, personDTO.phoneNumber) && Objects.equals(enabled, personDTO.enabled);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, email, phoneNumber, enabled);
	}
}
