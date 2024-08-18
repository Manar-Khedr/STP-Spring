package com.sumerge.spring.service;


import com.sumerge.spring.classes.Author;
import com.sumerge.spring.dto.AuthorDTO;
import com.sumerge.spring.mapper.AuthorMapper;
import com.sumerge.spring.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    // constructor
    @Autowired
    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper){
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    // methods
    public AuthorDTO addAuthor(AuthorDTO authorDTO) {
        Author author = authorMapper.mapToAuthor(authorDTO);
        Author savedAuthor = authorRepository.save(author);
        return authorMapper.mapToAuthorDTO(savedAuthor);
    }

    public AuthorDTO viewAuthor(int authorId) {
        return authorRepository.findById(authorId)
                .map(authorMapper::mapToAuthorDTO)
                .orElse(null);
    }

    public AuthorDTO updateAuthor(AuthorDTO authorDTO) {
        if (authorRepository.existsById(authorDTO.getAuthorId())) {
            Author author = authorMapper.mapToAuthor(authorDTO);
            Author updatedAuthor = authorRepository.save(author);
            return authorMapper.mapToAuthorDTO(updatedAuthor);
        }
        return null;
    }

    public void deleteAuthor(int authorId) {
        authorRepository.deleteById(authorId);
    }

    // view all authors within pagination
    public Page<AuthorDTO> viewAllAuthors(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return authorRepository.findAll(pageable)
                .map(authorMapper::mapToAuthorDTO);
    }

    // filter author by email
    public AuthorDTO getAuthorByEmail(String authorEmail) {
        return authorRepository.findByAuthorEmail(authorEmail)
                .map(authorMapper::mapToAuthorDTO)
                .orElse(null);
    }

}
