package com.sumerge.spring.mapper;

import com.sumerge.spring3.classes.Author;
import com.sumerge.spring.dto.AuthorDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthorMapperTest {

    private final AuthorMapper mapper = AuthorMapper.INSTANCE;

    @Test
    public void testMapToAuthorDTO() {

        Author author = new Author();
        author.setAuthorId(1);
        author.setAuthorName("Test Author");
        author.setAuthorEmail("test@example.com");

        AuthorDTO authorDTO = mapper.mapToAuthorDTO(author);

        assertEquals(author.getAuthorId(), authorDTO.getAuthorId());
        assertEquals(author.getAuthorName(), authorDTO.getAuthorName());
        assertEquals(author.getAuthorEmail(), authorDTO.getAuthorEmail());
    }

    @Test
    public void testMapToAuthor() {

        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setAuthorId(1);
        authorDTO.setAuthorName("Test Author");
        authorDTO.setAuthorEmail("test@example.com");

        Author author = mapper.mapToAuthor(authorDTO);

        assertEquals(authorDTO.getAuthorId(), author.getAuthorId());
        assertEquals(authorDTO.getAuthorName(), author.getAuthorName());
        assertEquals(authorDTO.getAuthorEmail(), author.getAuthorEmail());
    }
}
