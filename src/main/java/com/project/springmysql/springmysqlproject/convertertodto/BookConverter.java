package com.project.springmysql.springmysqlproject.convertertodto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.project.springmysql.springmysqlproject.domain.Books;
import com.project.springmysql.springmysqlproject.dto.BookDTO;

public class BookConverter {
	
	static ModelMapper mapper = new ModelMapper();
	
	public static BookDTO convertBookToBookDTO(Books book) {
		BookDTO bookDto = mapper.map(book, BookDTO.class);
		return bookDto;
	}
	
	public static List<BookDTO> convertListOfBooksToListOfBooksDTO(List<Books> books){
		List<BookDTO> booksDto = new ArrayList<>();
		for(Books book : books) {
			booksDto.add(mapper.map(book, BookDTO.class));
		}
		return booksDto;
		
	}
	
	public static Books convertBookDTOtoBook(BookDTO bookDto) {
		Books book = mapper.map(bookDto, Books.class);
		return book;
	}
}
