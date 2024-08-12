package com.project.springmysql.springmysqlproject.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.springmysql.springmysqlproject.dto.UserDTO;
import com.project.springmysql.springmysqlproject.services.UserService;
import com.project.springmysql.springmysqlproject.util.MediaType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/users")
@Tag(name = "User", description = "Endpoints for managing users")
public class UserController {
	
	@Autowired
	private UserService userService;

	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Finds all users", description = "finds all users",
	tags = {"User"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = {@Content(
							mediaType = "application/json",
							array = @ArraySchema(schema = @Schema(implementation = UserDTO.class))
							)}),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			
	})
	public ResponseEntity<List<UserDTO>> findAll(){
		List<UserDTO> usersList = userService.findAll();
		return ResponseEntity.ok().body(usersList);
	}
	
	@GetMapping(value = "/{id}",produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Finds an user", description = "finds an user",
	tags = {"User"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = UserDTO.class))
							),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			
	})
	public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
		UserDTO user = userService.findById(id);
		return ResponseEntity.ok().body(user);
	}

	@CrossOrigin(origins = {"http://localhost:8080", "https://lucio.com.br"})
	@PostMapping(consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Adds a new user", description = "adds a new user by passing in a JSON, XML or YML representation of the user!",
	tags = {"User"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = @Content),
			@ApiResponse(description = "Created", responseCode = "201",content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			
	})
	public ResponseEntity<UserDTO> create(@RequestBody UserDTO obj){
		UserDTO user = userService.create(obj);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getKey())
                .toUri();
		return ResponseEntity.created(location).body(user);
	}
	
	@PutMapping(value = "/{id}",consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
	@Operation(summary = "Updates an user", description = "updates a user by passing in a JSON, XML or YML representation of the user!",
	tags = {"User"},
	responses = {
			@ApiResponse(description = "Updated", responseCode = "200",
					content = @Content),
			@ApiResponse(description = "No Content", responseCode = "204",content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			
	})
	public ResponseEntity<Void> update(@RequestBody UserDTO obj, @PathVariable Long id){
		obj.setKey(id);
		userService.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Deletes an user", description = "deletes an user by passing their id",
	tags = {"User"},
	responses = {
			@ApiResponse(description = "No Content", responseCode = "204",content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			
	})
	public ResponseEntity<Void> delete(@PathVariable Long id){
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
