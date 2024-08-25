package com.project.springmysql.springmysqlproject.controllers;

import java.net.URI;


import com.project.springmysql.springmysqlproject.dto.PersonDTO;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.springmysql.springmysqlproject.services.PersonsService;
import com.project.springmysql.springmysqlproject.util.MediaType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/api/person/v1")
@Tag(name = "Person", description = "Endpoints for managing persons")
public class PersonController {
	
	@Autowired
	private PersonsService personsService;

	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Finds all persons", description = "finds all persons",
	tags = {"Person"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = {@Content(
							mediaType = "application/json",
							array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class))
							)}),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			
	})
	public ResponseEntity<PagedModel<EntityModel<PersonDTO>>> findAll(@RequestParam(value = "page", defaultValue = "0")Integer page,
																	  @RequestParam(value = "size", defaultValue = "12")Integer size,
																	  @RequestParam(value = "direction", defaultValue = "asc") String direction) {

		var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));

		return ResponseEntity.ok(personsService.findAll(pageable));
	}

	@GetMapping(value = "/findPersonByName/{name}",produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Finds people by name", description = "finds people by name",
			tags = {"Person"},
			responses = {
					@ApiResponse(description = "Success", responseCode = "200",
							content = {@Content(
									mediaType = "application/json",
									array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class))
							)}),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),

			})
	public ResponseEntity<PagedModel<EntityModel<PersonDTO>>> findPersonsByName(@PathVariable(value = "name") String name,
																				@RequestParam(value = "page", defaultValue = "0")Integer page,
																	  @RequestParam(value = "size", defaultValue = "12")Integer size,
																	  @RequestParam(value = "direction", defaultValue = "asc") String direction) {

		var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));

		return ResponseEntity.ok(personsService.findPersonsByName(name,pageable));
	}
	
	@GetMapping(value = "/{id}",produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Finds a person", description = "finds a person",
	tags = {"Person"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = PersonDTO.class))
							),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			
	})
	public ResponseEntity<PersonDTO> findById(@PathVariable Long id) {
		PersonDTO user = personsService.findById(id);
		return ResponseEntity.ok().body(user);
	}

	@CrossOrigin(origins = {"http://localhost:8080", "https://lucio.com.br"})
	@PostMapping(consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Adds a new person", description = "adds a new person by passing in a JSON, XML or YML representation of the  person!",
	tags = {"Person"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = @Content),
			@ApiResponse(description = "Created", responseCode = "201",content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			
	})
	public ResponseEntity<PersonDTO> create(@RequestBody PersonDTO obj){
		PersonDTO user = personsService.create(obj);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getKey())
                .toUri();
		return ResponseEntity.created(location).body(user);
	}
	
	@PutMapping(value = "/{id}",consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Updates a person", description = "updates a person by passing in a JSON, XML or YML representation of the person!",
	tags = {"Person"},
	responses = {
			@ApiResponse(description = "Updated", responseCode = "200",
					content = @Content),
			@ApiResponse(description = "No Content", responseCode = "204",content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			
	})
	public ResponseEntity<PersonDTO> update(@RequestBody PersonDTO obj, @PathVariable Long id){
		obj.setKey(id);
		PersonDTO updatedPerson = personsService.update(obj);
		return ResponseEntity.ok(updatedPerson);
	}

	@PatchMapping(value = "/{id}",produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Disable a specific person by your id", description = "disable a specific person by your id",
			tags = {"Person"},
			responses = {
					@ApiResponse(description = "Success", responseCode = "200",
							content = @Content(schema = @Schema(implementation = PersonDTO.class))
					),
					@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),

			})
	public ResponseEntity<PersonDTO> disablePerson(@PathVariable Long id) {
		PersonDTO user = personsService.disablePerson(id);
		return ResponseEntity.ok().body(user);
	}
	
	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Deletes a person", description = "deletes a person by passing their id",
	tags = {"Person"},
	responses = {
			@ApiResponse(description = "No Content", responseCode = "204",content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			
	})
	public ResponseEntity<Void> delete(@PathVariable Long id){
		personsService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
