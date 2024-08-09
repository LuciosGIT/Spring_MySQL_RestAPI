package com.project.springmysql.springmysqlproject.unittests.mockito.services;




import static org.mockito.ArgumentMatchers.any;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

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

import com.project.springmysql.springmysqlproject.domain.User;
import com.project.springmysql.springmysqlproject.dto.UserDTO;
import com.project.springmysql.springmysqlproject.exceptions.ObjectNotFoundException;
import com.project.springmysql.springmysqlproject.repositories.UserRepository;
import com.project.springmysql.springmysqlproject.services.UserService;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	
	@InjectMocks
	private UserService userService;
	
	@Mock
	private UserRepository userRepository;
	
	
	private User user;
	
	private UserDTO userDto;
	
	private Optional<User> optionalUser;
	
	@BeforeEach
	void setUpMocks() {
		MockitoAnnotations.openMocks(this);
		user = new User(1L ,"Carlos", "carlos@mail.com", "284219412");
		userDto = new UserDTO(1L, "Carlos", "carlos@mail.com", "284219412");
		optionalUser = Optional.of(new User(1L ,"Carlos", "carlos@mail.com", "284219412"));
		
	}
	
	
	@Test
	@DisplayName("when find by id then return an user instance")
	void whenFindByIdThenReturnAnUserInstance() {
		
		when(userRepository.findById(Mockito.anyLong())).thenReturn(optionalUser);
		when(userRepository.findById(9999L)).thenReturn(Optional.empty());
		
		
		UserDTO response = userService.findById(userDto.getKey());
		
		Assertions.assertThat(response).isNotNull();
		Assertions.assertThat(UserDTO.class).isEqualTo(response.getClass());
		Assertions.assertThat(response.getKey()).isEqualTo(userDto.getKey());
		Assertions.assertThat(response.getName()).isEqualTo(userDto.getName());
		Assertions.assertThatThrownBy(() -> userService.findById(9999L)).isInstanceOf(ObjectNotFoundException.class);
		
		
	
	
	}
	
	@Test
	@DisplayName("create return success")
	void whenCreateThenReturnSuccess() {
		when(userRepository.save(any())).thenReturn(user);
		User response = userService.create(userDto);
		
		Assertions.assertThat(response).isNotNull();
		Assertions.assertThat(response.getClass()).isEqualTo(User.class);
		Assertions.assertThat(response.getId()).isEqualTo(user.getId());
		
		
	}
	
	@Test
	@DisplayName("when create then return data integrity violation exception")
	void whenCreateThenReturnDataIntegrityViolationException() {
		
		when(userRepository.existsByEmail(anyString())).thenReturn(true);
		
		Assertions.assertThatThrownBy(() -> userService.create(userDto))
		.isInstanceOf(DataIntegrityViolationException.class);
		Assertions.assertThatThrownBy(() -> userService.create(userDto)).
		isInstanceOf(DataIntegrityViolationException.class).
		hasMessage("This email already exists!");
		
	}
	
	@Test
	@DisplayName("delete return success")
	void whenDeleteThenReturnSuccess() {
		
		when(userRepository.findById(anyLong())).thenReturn(optionalUser);
		doNothing().when(userRepository).deleteById(anyLong());
		userService.delete(userDto.getKey());
		
		verify(userRepository, times(1)).deleteById(anyLong());
		
		
	}
	
	@Test
	@DisplayName("when delete return object not found exception")
	void whenDeleteReturnObjectNotFoundException() {
		
		when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
		
		
		
		Assertions.assertThatThrownBy(() -> userService.delete(userDto.getKey())).isInstanceOf(ObjectNotFoundException.class);
		Assertions.assertThatThrownBy(() -> userService.delete(userDto.getKey())).isInstanceOf(ObjectNotFoundException.class).hasMessage("Object not found!");
		
	}
	
	
	
	
	
}
