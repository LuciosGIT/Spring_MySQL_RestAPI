package com.project.springmysql.springmysqlproject.unittests.mockito.services;





import static org.mockito.ArgumentMatchers.any;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.project.springmysql.springmysqlproject.dto.PersonDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import com.project.springmysql.springmysqlproject.domain.Person;
import com.project.springmysql.springmysqlproject.exceptions.ObjectNotFoundException;
import com.project.springmysql.springmysqlproject.exceptions.RequiredObjectIsNullException;
import com.project.springmysql.springmysqlproject.repositories.PersonRepository;
import com.project.springmysql.springmysqlproject.services.PersonsService;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
	
	@InjectMocks
	private PersonsService userService;
	
	@Mock
	private PersonRepository userRepository;
	
	
	private Person user;
	
	private PersonDTO personDto;
	
	private Optional<Person> optionalUser;
	
	@BeforeEach
	void setUpMocks() {
		MockitoAnnotations.openMocks(this);
		user = new Person(1L ,"Carlos", "carlos@mail.com", "284219412");
		personDto = new PersonDTO(1L, "Carlos", "carlos@mail.com", "284219412");
		optionalUser = Optional.of(new Person(1L ,"Carlos", "carlos@mail.com", "284219412"));
		
	}
	
	
	@Test
	@DisplayName("when find by id then return an user instance")
	void whenFindByIdThenReturnAnUserInstance() {
		
		when(userRepository.findById(Mockito.anyLong())).thenReturn(optionalUser);
		when(userRepository.findById(9999L)).thenReturn(Optional.empty());
		
		
		PersonDTO response = userService.findById(personDto.getKey());
		
		Assertions.assertThat(response).isNotNull();
		Assertions.assertThat(PersonDTO.class).isEqualTo(response.getClass());
		Assertions.assertThat(response.getKey()).isEqualTo(personDto.getKey());
		Assertions.assertThat(response.getName()).isEqualTo(personDto.getName());
		Assertions.assertThatThrownBy(() -> userService.findById(9999L)).isInstanceOf(ObjectNotFoundException.class);
		
		
	
	
	}
	
	@Test
	@DisplayName("create return success")
	void whenCreateThenReturnSuccess() {
		when(userRepository.save(any())).thenReturn(user);
		PersonDTO response = userService.create(personDto);
		
		Assertions.assertThat(response).isNotNull();
		Assertions.assertThat(response.getClass()).isEqualTo(PersonDTO.class);
		Assertions.assertThat(response.getKey()).isEqualTo(user.getId());
		
		
	}
	
	@Test
	@DisplayName("when create then return data integrity violation exception")
	void whenCreateThenReturnDataIntegrityViolationException() {
		
		when(userRepository.existsByEmail(anyString())).thenReturn(true);
		
		Assertions.assertThatThrownBy(() -> userService.create(personDto))
		.isInstanceOf(DataIntegrityViolationException.class);
		Assertions.assertThatThrownBy(() -> userService.create(personDto)).
		isInstanceOf(DataIntegrityViolationException.class).
		hasMessage("This email already exists!");
		
	}
	
	@Test
	@DisplayName("delete return success")
	void whenDeleteThenReturnSuccess() {
		
		when(userRepository.findById(anyLong())).thenReturn(optionalUser);
		doNothing().when(userRepository).deleteById(anyLong());
		userService.delete(personDto.getKey());
		
		verify(userRepository, times(1)).deleteById(anyLong());
		
		
	}
	
	@Test
	@DisplayName("when delete return object not found exception")
	void whenDeleteReturnObjectNotFoundException() {
		
		when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
		
		Assertions.assertThatThrownBy(() -> userService.delete(personDto.getKey())).isInstanceOf(ObjectNotFoundException.class);
		Assertions.assertThatThrownBy(() -> userService.delete(personDto.getKey())).isInstanceOf(ObjectNotFoundException.class).hasMessage("Object not found!");
		
	}
	
	@Test
	@DisplayName("when update return object not found exception")
	void whenUpdateReturnObjectNotFoundException() {
		
		when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
		
		Assertions.assertThatThrownBy(() -> userService.update(personDto)).isInstanceOf(ObjectNotFoundException.class);
		Assertions.assertThatThrownBy(() -> userService.update(personDto)).isInstanceOf(ObjectNotFoundException.class).hasMessage("Object not found!");
		
	}
	
	@Test
	@DisplayName("when tries to create null user returns RequiredObjectIsNull exception")
	void whenTriesToCreateNullUserReturnsRequiredObjectNotFoundException() {
		
		
		Assertions.assertThatThrownBy(() -> userService.create(null)).isInstanceOf(RequiredObjectIsNullException.class);
		Assertions.assertThatThrownBy(() -> userService.create(null)).isInstanceOf(RequiredObjectIsNullException.class).hasMessage("You cannot save null objects!");
	}
	
	@Test
	@DisplayName("when tries to update null user returns exception")
	void whenTriesToUpdateNullUserReturnsRequiredObjectNotFoundException() {
		
		
		Assertions.assertThatThrownBy(() -> userService.update(null)).isInstanceOf(RequiredObjectIsNullException.class);
		Assertions.assertThatThrownBy(() -> userService.update(null)).isInstanceOf(RequiredObjectIsNullException.class).hasMessage("You cannot save null objects!");
	}
}
