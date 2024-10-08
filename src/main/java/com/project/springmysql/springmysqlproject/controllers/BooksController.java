package com.project.springmysql.springmysqlproject.controllers;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.springmysql.springmysqlproject.dto.BookDTO;

import com.project.springmysql.springmysqlproject.services.BooksService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;



@RestController
@RequestMapping(value = "/api/book/v1")
public class BooksController {
	
	@Autowired
	BooksService bookService;

	@GetMapping
	@Operation(summary = "Finds all books", description = "finds all books",
	tags = {"Books"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = {@Content(
							mediaType = "application/json",
							array = @ArraySchema(schema = @Schema(implementation = BookDTO.class))
							)}),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			
	})
	public ResponseEntity<PagedModel<EntityModel<BookDTO>>> findAll(@RequestParam (value = "page", defaultValue = "0") Integer page,
																	@RequestParam (value = "size", defaultValue = "12")Integer size,
																	@RequestParam (value = "direction", defaultValue = "asc")String direction){

		var pageDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

		Pageable pageable = PageRequest.of(page, size, Sort.by(pageDirection, "title"));

		return ResponseEntity.ok(bookService.findAll(pageable));
	}
	
	@GetMapping(value = "/{id}")
	@Operation(summary = "Finds a book", description = "finds a book",
	tags = {"Books"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = BookDTO.class))
							),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			
	})
	public ResponseEntity<BookDTO> findById(@PathVariable Long id){
		BookDTO book = bookService.findById(id);
		return ResponseEntity.ok().body(book);
	}
	
	@PostMapping
	@Operation(summary = "Adds a new book", description = "adds a new book by passing in a JSON, XML or YML representation of the book!",
	tags = {"Books"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = @Content),
			@ApiResponse(description = "Created", responseCode = "201",content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			
	})
	public ResponseEntity<Void> create(@RequestBody BookDTO obj){
		bookService.create(obj);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri(); 
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping(value = "/{id}")
	@Operation(summary = "Updates a book", description = "updates a book by passing in a JSON, XML or YML representation of the book!",
	tags = {"Books"},
	responses = {
			@ApiResponse(description = "Updated", responseCode = "200",
					content = @Content),
			@ApiResponse(description = "No Content", responseCode = "204",content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			
	})
	public ResponseEntity<Void> update(@PathVariable Long id,@RequestBody BookDTO obj){
		obj.setId(id);
		bookService.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Deletes a book", description = "deletes a book by passing its id",
	tags = {"Books"},
	responses = {
			@ApiResponse(description = "No Content", responseCode = "204",content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			
	})
	public ResponseEntity<Void> deleteById(@PathVariable Long id){
		bookService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	
}
