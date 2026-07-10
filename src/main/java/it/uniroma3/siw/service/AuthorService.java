package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.repository.AuthorRepository;
import jakarta.transaction.Transactional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    public Optional<Author> findAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    public Optional<Author> findByIdWithBooks(Long id) {
        return authorRepository.findByIdWithBooks(id);
    }

    public boolean isDuplicate(Author author) {

        if (author.getName() == null ||
            author.getSurname() == null ||
            author.getBirthDate() == null) {
            return false;
        }

        String normalizedName = author.getName().trim();
        String normalizedSurname = author.getSurname().trim();

        if (author.getId() == null) {
            return authorRepository
                    .existsByNameIgnoreCaseAndSurnameIgnoreCaseAndBirthDate(
                            normalizedName,
                            normalizedSurname,
                            author.getBirthDate()
                    );
        }

        return authorRepository
                .existsByNameIgnoreCaseAndSurnameIgnoreCaseAndBirthDateAndIdNot(
                        normalizedName,
                        normalizedSurname,
                        author.getBirthDate(),
                        author.getId()
                );
    }

    @Transactional
    public Author saveAuthor(Author author) {
        author.setName(author.getName().trim());
        author.setSurname(author.getSurname().trim());

        return authorRepository.save(author);
    }

    @Transactional
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}