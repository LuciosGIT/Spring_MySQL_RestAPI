package com.project.springmysql.springmysqlproject.convertertodto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.project.springmysql.springmysqlproject.domain.User;
import com.project.springmysql.springmysqlproject.dto.UserDTO;

public class UserConverter {
	
	private static ModelMapper mapper = new ModelMapper();
	
	public static UserDTO convertUserToUserDto(User obj) {
		UserDTO userDto = mapper.map(obj, UserDTO.class);
		return userDto;
	}
	
	public static List<UserDTO> convertListofUserToListOfUserDto(List<User> users){
		List<UserDTO> usersDto = new ArrayList<>();
		for(User user : users) {
			usersDto.add(mapper.map(user, UserDTO.class));
		}
		return usersDto;
	}
	
	public static User convertUserDtoToUser(UserDTO obj) {
		User userDto = mapper.map(obj, User.class);
		return userDto;
	}
}
