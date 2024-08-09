package com.project.springmysql.springmysqlproject.services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.project.springmysql.springmysqlproject.controllers.UserController;
import com.project.springmysql.springmysqlproject.convertertodto.UserConverter;
import com.project.springmysql.springmysqlproject.domain.User;
import com.project.springmysql.springmysqlproject.dto.UserDTO;
import com.project.springmysql.springmysqlproject.exceptions.ObjectNotFoundException;
import com.project.springmysql.springmysqlproject.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public UserDTO findById(Long id) {
		UserDTO user = UserConverter.convertUserToUserDto(userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Object not found!")));
		user.add(linkTo(methodOn(UserController.class).findById(id)).withSelfRel());
		return user;
	}
	
	public List<UserDTO> findAll(){
		List<UserDTO> usersList = UserConverter.convertListofUserToListOfUserDto(userRepository.findAll());
		usersList.stream().forEach(p -> p.add(linkTo(methodOn(UserController.class).findById(p.getKey())).withSelfRel()));
		return usersList;
	}
	
	public User create(UserDTO obj) {
		if(userRepository.existsByEmail(obj.getEmail())) {
			throw new DataIntegrityViolationException("This email already exists!");
		}
		if(userRepository.existsByPhoneNumber(obj.getPhoneNumber())) {
			throw new DataIntegrityViolationException("This phone number is already registered!");
		}
		return userRepository.save(UserConverter.convertUserDtoToUser(obj));
	}
	
	public void update(UserDTO obj) {
		UserDTO newObj = findById(obj.getKey());
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
		newObj.setPhoneNumber(obj.getPhoneNumber());
		userRepository.save(UserConverter.convertUserDtoToUser(newObj));
	}
	
	public void delete(Long id) {
		findById(id);
		userRepository.deleteById(id);
	}

}
