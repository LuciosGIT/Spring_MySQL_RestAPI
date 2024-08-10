package com.project.springmysql.springmysqlproject.unittests.mockito.services;

import static org.mockito.ArgumentMatchers.anyLong;
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
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.project.springmysql.springmysqlproject.domain.Books;
import com.project.springmysql.springmysqlproject.dto.BookDTO;
import com.project.springmysql.springmysqlproject.exceptions.ObjectNotFoundException;
import com.project.springmysql.springmysqlproject.repositories.BooksRepository;
import com.project.springmysql.springmysqlproject.services.BooksService;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class BooksServiceTest {
	
	@InjectMocks
	private BooksService service;
	
	@Mock
	private BooksRepository repository;
	
	private Books book;
	private BookDTO bookDto;
	private Optional<Books> optionalBooks;
	
	
	@BeforeEach
	void setUp(){
		MockitoAnnotations.openMocks(this);
		book = new Books(1L, "Lord of the Rings", "J.R.R Tolkien");
		bookDto = new BookDTO(1L, "Lord of the Rings", "J.R.R Tolkien");
		optionalBooks = Optional.of(new Books(1L, "Lord of the Rings", "J.R.R Tolkien"));
	}
	
	
	@Test
	@DisplayName("find by id returns a book when successful")
	void FindByIdReturnBookWhenSuccessful() {
		
		when(repository.findById(anyLong())).thenReturn(optionalBooks);
		
		BookDTO response = service.findById(bookDto.getId());
		
		Assertions.assertThat(response).isNotNull();
		Assertions.assertThat(bookDto.getClass()).isEqualTo(response.getClass());
		Assertions.assertThat(response.getId()).isEqualTo(bookDto.getId());
		
	}
	
	@Test
	@DisplayName("throws object not found exception when not successful")
	void ThrowsObjectNotFoundExceptionWhenNotSuccessful() {
		
		when(repository.findById(9999L)).thenThrow(new ObjectNotFoundException("Object Not Found!"));
		
		Assertions.assertThatThrownBy(() -> service.findById(9999L)).isInstanceOf(ObjectNotFoundException.class);
		Assertions.assertThatThrownBy(() -> service.findById(9999L)).hasMessage("Object Not Found!");
	}
	
	@Test
	@DisplayName("creates a new book when successful")
	void createsANewBookWhenSuccessful() {
		
		when(repository.save(book)).thenReturn(book);
		
		BookDTO response = service.create(bookDto);
		
		Assertions.assertThat(response).isNotNull();
		Assertions.assertThat(response.getClass()).isEqualTo(BookDTO.class);
		Assertions.assertThat(response.getId()).isEqualTo(bookDto.getId());		
	}
	
	@Test
	@DisplayName("deletes a book when successful")
	void deletesABookWhenSuccessful() {
		
		when(repository.findById(anyLong())).thenReturn(optionalBooks);
		
		doNothing().when(repository).deleteById(anyLong());
		
		service.delete(bookDto.getId());
		
		
		verify(repository, times(1)).deleteById(anyLong());
		
	}
	
	@Test
	@DisplayName("when delete throwns object not found exception")
	void whenDeleteThrowsObjectNotFoundException() {
		
		when(repository.findById(anyLong())).thenReturn(Optional.empty());
		

		
		Assertions.assertThatThrownBy(() -> service.delete(bookDto.getId())).isInstanceOf(ObjectNotFoundException.class);
		
		Assertions.assertThatThrownBy(() -> service.delete(bookDto.getId())).hasMessage("Object Not Found!");
		
	}
	
	@Test
	@DisplayName("")
	void whenUpdatesReturnObjectNotFoundException() {
		
		when(repository.findById(anyLong())).thenReturn(Optional.empty());
		
		Assertions.assertThatThrownBy(() -> service.update(bookDto)).isInstanceOf(ObjectNotFoundException.class);
		Assertions.assertThatThrownBy(() -> service.update(bookDto)).hasMessage("Object Not Found!");
	}
	
	
	
}
