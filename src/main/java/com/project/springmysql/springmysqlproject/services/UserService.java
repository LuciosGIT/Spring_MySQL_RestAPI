package com.project.springmysql.springmysqlproject.services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.project.springmysql.springmysqlproject.domain.User;
import com.project.springmysql.springmysqlproject.exceptions.ObjectNotFoundException;
import com.project.springmysql.springmysqlproject.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User findById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Object not found!"));
	}
	
	public List<User> findAll(){
		List<User> users = userRepository.findAll();
		return users;
	}
	
	public void create(User obj) {
		if(userRepository.existsByEmail(obj.getEmail())) {
			throw new DataIntegrityViolationException("This email already exists!");
		}
		if(userRepository.existsByPhoneNumber(obj.getPhoneNumber())) {
			throw new DataIntegrityViolationException("This phone number is already registered!");
		}
		userRepository.save(obj);
	}
	
	public void update(User obj) {
		User newObj = findById(obj.getId());
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
		newObj.setPhoneNumber(obj.getPhoneNumber());
		userRepository.save(newObj);
	}
	
	public void delete(Long id) {
		findById(id);
		userRepository.deleteById(id);
	}

}
