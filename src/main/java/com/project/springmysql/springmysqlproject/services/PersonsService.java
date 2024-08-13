package com.project.springmysql.springmysqlproject.services;

import java.util.List;


import com.project.springmysql.springmysqlproject.dto.PersonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.dao.DataIntegrityViolationException;
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
	
	public List<PersonDTO> findAll(){
		List<PersonDTO> usersList = PersonConverter.convertListofUserToListOfUserDto(userRepository.findAll());
		usersList.stream().forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
		return usersList;
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
	
	public void update(PersonDTO obj) {
		if(obj == null) {
			throw new RequiredObjectIsNullException("You cannot save null objects!");
		}
		PersonDTO newObj = findById(obj.getKey());
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
		newObj.setPhoneNumber(obj.getPhoneNumber());
		userRepository.save(PersonConverter.convertUserDtoToUser(newObj));
	}
	
	public void delete(Long id) {
		findById(id);
		userRepository.deleteById(id);
	}

}
