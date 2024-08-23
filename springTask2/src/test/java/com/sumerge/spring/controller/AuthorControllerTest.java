package com.sumerge.spring.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumerge.spring.dto.AuthorDTO;
import com.sumerge.spring.exception.ResourceNotFoundException;
import com.sumerge.spring.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.validation.ValidationException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(controllers = AuthorController.class)
@ContextConfiguration(classes = AuthorController.class)
@ComponentScan(basePackages = "exception")
@ExtendWith(SpringExtension.class)
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @Autowired
    private ObjectMapper objectMapper;

    private AuthorController authorController;



    private AuthorDTO authorDTO;
    private String authorEmail = "test@example.com";
    private String authorName = "Test Author";

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        authorDTO = new AuthorDTO("test@example.com","Test Author");
        authorController = new AuthorController(authorService);
    }

    @Test
    void testAddAuthor_Success() throws Exception {
        // initialize
        AuthorDTO createdAuthor = new AuthorDTO("test@example.com","Test Author");
        when(authorService.addAuthor(any(AuthorDTO.class))).thenReturn(createdAuthor);

        // testing
        mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authorEmail").value(authorEmail));
    }

    @Test
    void testAddAuthor_ValidationException() throws Exception{
        // Arrange

        doThrow(new ValidationException("Author with email test@example.com already exists."))
                .when(authorService).addAuthor(any(AuthorDTO.class));

        // Act & Assert
        mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorDTO)))
                .andExpect(status().isConflict());
    }

    @Test
    void testViewAuthorByEmail_Success() throws Exception{

        when(authorService.viewAuthorByEmail(authorEmail)).thenReturn(authorDTO);

        mockMvc.perform(get("/authors/{authorEmail}", authorEmail)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authorEmail").value(authorEmail));
    }

    @Test
    void testViewAuthorByEmail_ResourceNotFoundException() throws Exception{
        // Arrange
        String authorEmail = "notfound@example.com";

        when(authorService.viewAuthorByEmail(authorEmail)).thenThrow(new ResourceNotFoundException("Author not found with email: " + authorEmail));

        // Act & Assert
        mockMvc.perform(get("/authors/{authorEmail}", authorEmail)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateAuthor_Success() throws Exception{
        // initialize
        AuthorDTO updatedAuthor = new AuthorDTO("test@example.com","Test Author");

        when(authorService.updateAuthor(any(AuthorDTO.class))).thenReturn(updatedAuthor);

        mockMvc.perform(put("/authors/{authorEmail}",authorEmail)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authorEmail").value(authorEmail));
    }

    @Test
    void testUpdateAuthor_ResourceNotFoundException() throws Exception{

        authorDTO.setAuthorEmail("notfound@example.com");

        when(authorService.updateAuthor(any(AuthorDTO.class))).thenThrow(new ResourceNotFoundException("Author not found with email: " + "notfound@example.com"));

        mockMvc.perform(put("/authors/{authorEmail}", "notfound@example.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteAuthorByEmail_Success() throws Exception{

        doNothing().when(authorService).deleteAuthorByEmail(authorEmail);

        // Perform the delete request
        mockMvc.perform(MockMvcRequestBuilders.delete("/authors/{authorEmail}", authorEmail))
                .andExpect(status().isOk());

        // Verify the service method was called with the correct parameter
        verify(authorService).deleteAuthorByEmail(authorEmail);
    }

    @Test
    void testDeleteAuthorByEmail_ResourceNotFoundException() throws Exception{
        // Mock the service to throw ResourceNotFoundException
        doThrow(new ResourceNotFoundException("Author not found with email: " + "notfound@example.com"))
                .when(authorService).deleteAuthorByEmail("notfound@example.com");

        // Perform the delete request and expect NOT_FOUND status
        mockMvc.perform(MockMvcRequestBuilders.delete("/authors/{authorEmail}", "notfound@example.com"))
                .andExpect(status().isNotFound());

        // Verify the service method was called with the correct parameter
        verify(authorService).deleteAuthorByEmail("notfound@example.com");
    }

    @Test
    public void testViewAllAuthors_NonEmptyPage() {
        // Arrange
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        List<AuthorDTO> authorList = List.of(new AuthorDTO(/* initialize with required fields */));
        Page<AuthorDTO> authorPage = new PageImpl<>(authorList, pageable, authorList.size());

        when(authorService.viewAllAuthors(page, size)).thenReturn(authorPage);

        // Act
        ResponseEntity<Page<AuthorDTO>> response = new ResponseEntity<>(authorService.viewAllAuthors(page, size),HttpStatus.OK);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authorPage, response.getBody());
        verify(authorService, times(1)).viewAllAuthors(page, size);
    }

    @Test
    public void testViewAllAuthors_Success() {
        // Arrange
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        List<AuthorDTO> authorList = List.of(authorDTO);
        Page<AuthorDTO> authorPage = new PageImpl<>(authorList, pageable, authorList.size());

        when(authorService.viewAllAuthors(anyInt(), anyInt())).thenReturn(authorPage);

        // Act
        ResponseEntity<Page<AuthorDTO>> response = authorController.viewAllAuthors(page, size);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authorPage, response.getBody());
        verify(authorService, times(1)).viewAllAuthors(page, size);
    }
}

