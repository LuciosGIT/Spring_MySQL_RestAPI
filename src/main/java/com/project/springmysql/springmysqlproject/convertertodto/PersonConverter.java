package com.project.springmysql.springmysqlproject.convertertodto;

import java.util.ArrayList;
import java.util.List;

import com.project.springmysql.springmysqlproject.dto.PersonDTO;
import org.modelmapper.ModelMapper;

import com.project.springmysql.springmysqlproject.domain.Person;

public class PersonConverter {
	
	private static ModelMapper mapper = new ModelMapper();
	
	public static PersonDTO convertUserToUserDto(Person obj) {
		PersonDTO personDto = mapper.map(obj, PersonDTO.class);
		return personDto;
	}
	
	static {
		mapper.createTypeMap(
				Person.class, PersonDTO.class)
		.addMapping(Person::getId, PersonDTO::setKey);
		}
	public static List<PersonDTO> convertListofUserToListOfUserDto(List<Person> users){
		List<PersonDTO> usersDto = new ArrayList<>();
		for(Person user : users) {
			usersDto.add(mapper.map(user, PersonDTO.class));
		}
		return usersDto;
	}
	
	public static Person convertUserDtoToUser(PersonDTO obj) {
		Person user = mapper.map(obj, Person.class);
		return user;
	}
}
