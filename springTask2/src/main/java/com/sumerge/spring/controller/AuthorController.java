package com.sumerge.spring.controller;


import com.sumerge.spring.exception.ResourceNotFoundException;
import com.sumerge.spring.dto.AuthorDTO;
import com.sumerge.spring.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

    @PostMapping
    // Add a new author (POST mapping)
    public ResponseEntity<AuthorDTO> addAuthor(@RequestBody AuthorDTO authorDTO) {
        try {
            AuthorDTO createdAuthor = authorService.addAuthor(authorDTO);
            return new ResponseEntity<>(createdAuthor, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    // View author by email (GET mapping)
    @GetMapping("/{authorEmail}")
    public ResponseEntity<AuthorDTO> viewAuthorByEmail(@PathVariable String authorEmail) {
        try{
            AuthorDTO author = authorService.viewAuthorByEmail(authorEmail);
            return new ResponseEntity<>(author, HttpStatus.OK);
        } catch (ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update an existing author (PUT mapping)
    @PutMapping("/{authorEmail}")
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable String authorEmail, @RequestBody AuthorDTO authorDTO) {
        try{
            authorDTO.setAuthorEmail(authorEmail);
            AuthorDTO updatedAuthor = authorService.updateAuthor(authorDTO);
            return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
        } catch( ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete author by email (DELETE mapping)
    @DeleteMapping("/{authorEmail}")
    public ResponseEntity<Void> deleteAuthorByEmail(@PathVariable String authorEmail) {
        try{
            authorService.deleteAuthorByEmail(authorEmail);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // View all authors using pagination (GET mapping)
    @GetMapping
    public ResponseEntity<Page<AuthorDTO>> viewAllAuthors(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        Page<AuthorDTO> authors = authorService.viewAllAuthors(page, size);
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }
}
