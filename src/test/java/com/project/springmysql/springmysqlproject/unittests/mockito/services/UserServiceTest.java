package com.project.springmysql.springmysqlproject.unittests.mockito.services;



import static org.mockito.Mockito.when;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

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
	
	
	@BeforeEach
	void setUpMocks() throws Exception{
		User user = new User();
		user.setId(1L);
		
		when(userRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(user));
		
		when(userRepository.findById(9999L)).thenReturn(Optional.empty());
	}
	
	
	@Test
	@DisplayName("find by id returns userDTO when successful")
	void FindByIdReturnsUserWhenSuccessful() {
		
		UserDTO userTest = new UserDTO();
		userTest.setKey(1L);
		
		UserDTO actualUserDTO = userService.findById(1L);
		
		Assertions.assertThat(userTest.getKey()).isEqualTo(actualUserDTO.getKey());
		Assertions.assertThat(UserDTO.class).isEqualTo(actualUserDTO.getClass());
		Assertions.assertThat(userTest).isNotNull();
		Assertions.assertThatThrownBy(() -> userService.findById(9999L))
	    .isInstanceOf(ObjectNotFoundException.class);
	
	
}
}
