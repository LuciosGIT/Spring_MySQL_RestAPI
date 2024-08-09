package com.project.springmysql.springmysqlproject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.springmysql.springmysqlproject.convertertodto.BookConverter;
import com.project.springmysql.springmysqlproject.dto.BookDTO;
import com.project.springmysql.springmysqlproject.exceptions.ObjectNotFoundException;
import com.project.springmysql.springmysqlproject.repositories.BooksRepository;

@Service
public class BooksService {
	
	@Autowired
	private BooksRepository booksRepository;
	
	
	public List<BookDTO> findAll(){
		List<BookDTO> listOfBooks = BookConverter.convertListOfBooksToListOfBooksDTO(booksRepository.findAll());
		return listOfBooks;
	}
	
	public BookDTO findById(Long id) {
		BookDTO bookDto = BookConverter.convertBookToBookDTO(booksRepository
				.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Object Not Found!")));
		return bookDto;
	}
	
	public void create(BookDTO book) {
		booksRepository.save(BookConverter.convertBookDTOtoBook(book));
	}
	
	public void update(BookDTO book) {
		BookDTO newBook = findById(book.getId());
		newBook.setId(book.getId());
		newBook.setTitle(book.getTitle());
		newBook.setAuthor(book.getAuthor());
		booksRepository.save(BookConverter.convertBookDTOtoBook(newBook));
	}
	
	public void delete(Long id) {
		findById(id);
		booksRepository.deleteById(id);
	}
}
