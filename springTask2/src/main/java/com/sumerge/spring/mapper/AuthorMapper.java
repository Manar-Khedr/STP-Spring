package com.sumerge.spring.mapper;

import com.sumerge.spring3.classes.Author;
import com.sumerge.spring.dto.AuthorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorMapper {

    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    AuthorDTO mapToAuthorDTO(Author author);
    Author mapToAuthor(AuthorDTO authorDTO);
}
