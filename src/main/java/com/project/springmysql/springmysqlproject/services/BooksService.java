package com.project.springmysql.springmysqlproject.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.springmysql.springmysqlproject.controllers.BooksController;
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
		listOfBooks.stream().forEach(p -> p.add(linkTo(methodOn(BooksController.class).findById(p.getId())).withSelfRel()));
		return listOfBooks;
	}
	
	public BookDTO findById(Long id) {
		BookDTO bookDto = BookConverter.convertBookToBookDTO(booksRepository
				.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Object Not Found!")));
		bookDto.add(linkTo(methodOn(BooksController.class).findById(id)).withSelfRel());
		return bookDto;
	}
	
	public void create(BookDTO book) {
		booksRepository.save(BookConverter.convertBookDTOtoBook(book));
	}
	
	public void update(BookDTO book) {
		BookDTO newBook = findById(book.getId());
		newBook.setTitle(book.getTitle());
		newBook.setAuthor(book.getAuthor());
		booksRepository.save(BookConverter.convertBookDTOtoBook(newBook));
	}
	
	public void delete(Long id) {
		findById(id);
		booksRepository.deleteById(id);
	}
}
