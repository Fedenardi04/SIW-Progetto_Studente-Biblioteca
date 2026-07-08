package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.repository.AuthorRepository;
import jakarta.transaction.Transactional;

@Service
public class AuthorService {

	private AuthorRepository authorRepository;

	public AuthorService(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}

    public List<Author> findAllAuthors() {
        return this.authorRepository.findAll();
    }

    public Optional<Author> findAuthorById(Long id) {
        return this.authorRepository.findById(id);
    }

    public Optional<Author> findByIdWithBooks(Long id) {
        return this.authorRepository.findByIdWithBooks(id);
    }

    @Transactional
    public Author saveAuthor(Author author) {

        return authorRepository.save(author);


    }

    @Transactional
    public void deleteAuthor(Long id) {
        this.authorRepository.deleteById(id);
    }
}
