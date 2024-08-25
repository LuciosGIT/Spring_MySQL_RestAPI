package com.project.springmysql.springmysqlproject.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
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

	@Autowired
	private PagedResourcesAssembler<BookDTO> assembler;
	
	
	public PagedModel<EntityModel<BookDTO>> findAll(Pageable pageable){
		var booksPage = booksRepository.findAll(pageable);

		var bookDtosPage = booksPage.map(b -> BookConverter.convertBookToBookDTO(b));

		bookDtosPage.stream().forEach(p -> p.add(linkTo(methodOn(BooksController.class).findById(p.getId())).withSelfRel()));

		Link link = linkTo(methodOn(BooksController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();

		return assembler.toModel(bookDtosPage, link);
	}
	
	public BookDTO findById(Long id) {
		BookDTO bookDto = BookConverter.convertBookToBookDTO(booksRepository
				.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Object Not Found!")));
		bookDto.add(linkTo(methodOn(BooksController.class).findById(id)).withSelfRel());
		return bookDto;
	}
	
	public BookDTO create(BookDTO book) {
		BookDTO bookDto = BookConverter
				.convertBookToBookDTO(booksRepository
						.save(BookConverter.convertBookDTOtoBook(book)));
		return bookDto;
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
