package it.uniroma3.siw.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.uniroma3.siw.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
	
	boolean existsByNameAndSurnameAndBirthDate(
            String name,
            String surname,
            LocalDate birthDate
    );
	
	Optional<Author> findByNameAndSurname(String name, String surname);

    @Query("""
           SELECT a
           FROM Author a
           LEFT JOIN FETCH a.books
           WHERE a.id = :id
           """)
    Optional<Author> findByIdWithBooks(Long id);

}
