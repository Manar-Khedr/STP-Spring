package com.sumerge.spring.mapper;

import com.sumerge.spring3.classes.Author;
import com.sumerge.spring.dto.AuthorDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthorMapperTest {

    private final AuthorMapper mapper = AuthorMapper.INSTANCE;

    @Test
    public void testMapToAuthorDTO() {
        // Given
        Author author = new Author();
        author.setAuthorId(1);
        author.setAuthorName("Test Author");
        author.setAuthorEmail("test@example.com");

        // When
        AuthorDTO authorDTO = mapper.mapToAuthorDTO(author);

        // Then
        assertEquals(author.getAuthorId(), authorDTO.getAuthorId());
        assertEquals(author.getAuthorName(), authorDTO.getAuthorName());
        assertEquals(author.getAuthorEmail(), authorDTO.getAuthorEmail());
    }

    @Test
    public void testMapToAuthor() {
        // Given
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setAuthorId(1);
        authorDTO.setAuthorName("Test Author");
        authorDTO.setAuthorEmail("test@example.com");

        // When
        Author author = mapper.mapToAuthor(authorDTO);

        // Then
        assertEquals(authorDTO.getAuthorId(), author.getAuthorId());
        assertEquals(authorDTO.getAuthorName(), author.getAuthorName());
        assertEquals(authorDTO.getAuthorEmail(), author.getAuthorEmail());
    }
}
