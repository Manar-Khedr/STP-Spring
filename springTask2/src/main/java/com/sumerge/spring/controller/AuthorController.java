package com.sumerge.spring.controller;


import com.sumerge.spring.classes.Author;
import com.sumerge.spring.dto.AuthorDTO;
import com.sumerge.spring.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

    // add a new author --> post mapping
    @PostMapping
    public AuthorDTO addAuthor(@RequestBody AuthorDTO authorDTO) {
        return authorService.addAuthor(authorDTO);
    }

    // view author by id --> get mapping
    @GetMapping("/{authorId}")
    public AuthorDTO viewAuthor(@PathVariable int authorId) {
        return authorService.viewAuthor(authorId);
    }

    // update an existing author --> put mapping
    @PutMapping("/{authorId}")
    public AuthorDTO updateAuthor(@PathVariable int authorId, @RequestBody AuthorDTO authorDTO) {
        authorDTO.setAuthorId(authorId);
        return authorService.updateAuthor(authorDTO);
    }

    // delete author by id --> delete mapping
    @DeleteMapping("/{authorId}")
    public void deleteAuthor(@PathVariable int authorId) {
        authorService.deleteAuthor(authorId);
    }

    // view all authors using pagination --> get mapping
    @GetMapping
    public Page<AuthorDTO> viewAllAuthors(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return authorService.viewAllAuthors(page, size);
    }

    // filter author by email --> get mapping
    @GetMapping("/search")
    public AuthorDTO getAuthorByEmail(@RequestParam String authorEmail) {
        return authorService.getAuthorByEmail(authorEmail);
    }

}
