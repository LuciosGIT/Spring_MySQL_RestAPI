package com.project.springmysql.springmysqlproject.services;

import com.project.springmysql.springmysqlproject.dto.PersonDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.springmysql.springmysqlproject.controllers.PersonController;
import com.project.springmysql.springmysqlproject.convertertodto.PersonConverter;
import com.project.springmysql.springmysqlproject.exceptions.ObjectNotFoundException;
import com.project.springmysql.springmysqlproject.exceptions.RequiredObjectIsNullException;
import com.project.springmysql.springmysqlproject.repositories.PersonRepository;

@Service
public class PersonsService {
	
	@Autowired
	private PersonRepository userRepository;
	
	public PersonDTO findById(Long id) {
		PersonDTO user = PersonConverter.convertUserToUserDto(userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Object not found!")));
		user.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return user;
	}
	
	public Page<PersonDTO> findAll(Pageable pageable){

		var personPage = userRepository.findAll(pageable);

		var personDtosPage = personPage.map(p -> PersonConverter.convertUserToUserDto(p));

		personDtosPage.map(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));

		return personDtosPage;
	}
	
	public PersonDTO create(PersonDTO obj) {
		if(obj == null) {
			throw new RequiredObjectIsNullException("You cannot save null objects!");
		}
		if(userRepository.existsByEmail(obj.getEmail())) {
			throw new DataIntegrityViolationException("This email already exists!");
		}
		if(userRepository.existsByPhoneNumber(obj.getPhoneNumber())) {
			throw new DataIntegrityViolationException("This phone number is already registered!");
		}
		return PersonConverter.convertUserToUserDto(userRepository.save(PersonConverter.convertUserDtoToUser(obj)));
	}
	
	public PersonDTO update(PersonDTO obj) {
		if(obj == null) {
			throw new RequiredObjectIsNullException("You cannot save null objects!");
		}
		PersonDTO newObj = findById(obj.getKey());
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
		newObj.setPhoneNumber(obj.getPhoneNumber());
		PersonDTO updatedPerson = PersonConverter.convertUserToUserDto(userRepository.save(PersonConverter.convertUserDtoToUser(newObj)));
		return updatedPerson;
	}

	@Transactional
	public PersonDTO disablePerson(Long id) {
		userRepository.disablePerson(id);

		var entity = userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Object Not Found!"));

		var dto = PersonConverter.convertUserToUserDto(entity);

		dto.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());

		return dto;
	}
	
	public void delete(Long id) {
		findById(id);
		userRepository.deleteById(id);
	}

}
