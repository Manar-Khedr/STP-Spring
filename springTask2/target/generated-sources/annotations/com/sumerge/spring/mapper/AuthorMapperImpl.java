package com.sumerge.spring.mapper;

import com.sumerge.spring.classes.Author;
import com.sumerge.spring.dto.AuthorDTO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-18T18:26:52+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.23 (Oracle Corporation)"
)
public class AuthorMapperImpl implements AuthorMapper {

    @Override
    public AuthorDTO mapToAuthorDTO(Author author) {
        if ( author == null ) {
            return null;
        }

        AuthorDTO authorDTO = new AuthorDTO();

        authorDTO.setAuthorId( author.getAuthorId() );
        authorDTO.setAuthorName( author.getAuthorName() );
        authorDTO.setAuthorEmail( author.getAuthorEmail() );
        authorDTO.setAuthorBirthDate( author.getAuthorBirthDate() );

        return authorDTO;
    }

    @Override
    public Author mapToAuthor(AuthorDTO authorDTO) {
        if ( authorDTO == null ) {
            return null;
        }

        Author author = new Author();

        author.setAuthorId( authorDTO.getAuthorId() );
        author.setAuthorName( authorDTO.getAuthorName() );
        author.setAuthorEmail( authorDTO.getAuthorEmail() );
        author.setAuthorBirthDate( authorDTO.getAuthorBirthDate() );

        return author;
    }
}
