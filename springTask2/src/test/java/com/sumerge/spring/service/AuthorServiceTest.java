package com.sumerge.spring.service;

import static org.junit.jupiter.api.Assertions.*;

import com.sumerge.spring.dto.AuthorDTO;
import com.sumerge.spring.exception.ResourceNotFoundException;
import com.sumerge.spring.mapper.AuthorMapper;
import com.sumerge.spring.repository.AuthorRepository;
import com.sumerge.spring3.classes.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.mockito.Mockito.*;

import javax.validation.ValidationException;
import java.util.Optional;
import java.util.*;

public class AuthorServiceTest {

    // mocks
    @Mock
    private AuthorMapper authorMapper;
    @Mock
    private AuthorRepository authorRepository;
    @InjectMocks
    private AuthorService authorService;

    // initialize
    private Author author;
    private Author savedAuthor;
    private AuthorDTO authorDTO;
    private String authorName = "Test Author";
    private String authorEmail = "test@example.com";

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        authorDTO = new AuthorDTO("test@example.com","Test Author");
        author = new Author();
        savedAuthor = new Author();
        // assign id
        savedAuthor.setAuthorId(1);
    }

    @Test
    void testAddAuthor_Success() throws ValidationException {
        // testing
        when(authorRepository.findByAuthorEmail(authorDTO.getAuthorEmail())).thenReturn(Optional.empty());
        // authorDTO --> author --> copying all fields
        when(authorMapper.mapToAuthor(authorDTO)).thenReturn(author);
        when(authorRepository.save(author)).thenReturn(savedAuthor);
        // savedAuthor --> authorDTO
        when(authorMapper.mapToAuthorDTO(savedAuthor)).thenReturn(authorDTO);

        // check results
        AuthorDTO result = authorService.addAuthor(authorDTO);

        assertNotNull(result,"The result should not be null");
        assertEquals(authorDTO.getAuthorEmail(),result.getAuthorEmail());
        assertEquals(authorDTO.getAuthorName(),result.getAuthorName());
    }

    @Test
    void testAddAuthor_ValidationException(){

        when(authorRepository.findByAuthorEmail(authorDTO.getAuthorEmail())).thenReturn(Optional.of(new Author()));

        // exception
        ValidationException exception = assertThrows(ValidationException.class, () -> authorService.addAuthor(authorDTO));

        // check results
        assertEquals("Author with email test@example.com already exists.",exception.getMessage());
    }

    @Test
    void testViewAuhtorByEmail_Success() throws ResourceNotFoundException{
        //initialize
        author.setAuthorName(authorName);
        author.setAuthorEmail(authorEmail);

        // arrange
        when(authorRepository.findByAuthorEmail(authorEmail)).thenReturn(Optional.of(author));
        // mapping
        when(authorMapper.mapToAuthorDTO(author)).thenReturn(authorDTO);

        // check results
        AuthorDTO result = authorService.viewAuthorByEmail(authorEmail);

        assertNotNull(result,"The result should not be null");
        assertEquals(authorEmail,result.getAuthorEmail());
        assertEquals(authorName, result.getAuthorName());
    }

    @Test
    void testViewAuthorByEmail_ResourseNotFoundException(){

        when(authorRepository.findByAuthorEmail(authorEmail)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> authorService.viewAuthorByEmail("notfound@example.com"));

        assertEquals("Author not found with email: notfound@example.com",exception.getMessage());
    }

    @Test
    void testUpdateAuthor_Success() throws ResourceNotFoundException{
        // initiliaze
        author.setAuthorEmail(authorEmail);
        author.setAuthorName("Test Author");
        authorDTO.setAuthorName("Updated Name");

        when(authorRepository.findByAuthorEmail(authorEmail)).thenReturn(Optional.of(author));
        when(authorRepository.save(author)).thenReturn(author);
        when(authorMapper.mapToAuthorDTO(author)).thenReturn(authorDTO);

        // check results
        AuthorDTO result = authorService.updateAuthor(authorDTO);

        assertNotNull(result,"The result should not be null");
        assertEquals(authorEmail,result.getAuthorEmail());
        assertEquals("Updated Name", result.getAuthorName());
    }

    @Test
    void testUpdateAuthor_ResourceNotFoundException(){

        when(authorRepository.findByAuthorEmail("notfound@example.com")).thenReturn(Optional.empty());

        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setAuthorEmail("notfound@example.com");

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> authorService.updateAuthor(authorDTO));

        assertEquals("Author not found with email: notfound@example.com", exception.getMessage());
    }

    @Test
    void testDeleteAuthorByEmail_Success() throws ResourceNotFoundException{
        // initailize
        author.setAuthorName(authorName);
        author.setAuthorEmail(authorEmail);

        when(authorRepository.findByAuthorEmail(authorEmail)).thenReturn(Optional.of(author));

        // checl results
        authorService.deleteAuthorByEmail(authorEmail);
        verify(authorRepository, times(1)).delete(author);
    }

    @Test
    void testDeleteByAuthorEmail_ResourceNotFoundException(){
        when(authorRepository.findByAuthorEmail(authorEmail)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> authorService.deleteAuthorByEmail("notfound@example.com"));

        assertEquals("Author not found with email: notfound@example.com",exception.getMessage());
    }

    @Test
    void testViewAllAuthors_Success(){
        // Arrange
        int page = 0;
        int size = 5;
        Pageable pageable = PageRequest.of(page, size);
        List<Author> authors = Arrays.asList(author);
        Page<Author> authorPage = new PageImpl<>(authors, pageable, authors.size());

        when(authorRepository.findAll(pageable)).thenReturn(authorPage);
        when(authorMapper.mapToAuthorDTO(author)).thenReturn(authorDTO);

        // Act
        Page<AuthorDTO> result = authorService.viewAllAuthors(page, size);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(authorDTO, result.getContent().get(0));
    }
}
