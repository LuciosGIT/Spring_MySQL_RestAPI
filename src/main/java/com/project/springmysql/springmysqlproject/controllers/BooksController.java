package com.project.springmysql.springmysqlproject.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.springmysql.springmysqlproject.dto.BookDTO;
import com.project.springmysql.springmysqlproject.services.BooksService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/books")
public class BooksController {
	
	@Autowired
	BooksService bookService;
	
	
	@GetMapping
	public ResponseEntity<List<BookDTO>> findAll(){
		List<BookDTO> books = bookService.findAll();
		return ResponseEntity.ok().body(books);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<BookDTO> findById(@PathVariable Long id){
		BookDTO book = bookService.findById(id);
		return ResponseEntity.ok().body(book);
	}
	
	@PostMapping
	public ResponseEntity<Void> create(@RequestBody BookDTO obj){
		bookService.create(obj);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri(); 
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id,@RequestBody BookDTO obj){
		obj.setId(id);
		bookService.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Long id){
		bookService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	
}
