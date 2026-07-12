package it.uniroma3.siw.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.uniroma3.siw.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByAuthorNameContainingIgnoreCase(String name);

    Optional<Book> findByTitleAndYear(String title, Integer year);

    boolean existsByTitleIgnoreCaseAndAuthorId(String title, Long authorId);

    boolean existsByTitleIgnoreCaseAndAuthorIdAndIdNot(String title, Long authorId, Long id);
    
    boolean existsByAuthorId(Long authorId);

    @Query("SELECT b FROM Book b JOIN FETCH b.author")
    List<Book> findAllWithAuthor();
    
    
    @EntityGraph(attributePaths = {"author"})
    @Query("SELECT b FROM Book b")
    List<Book> findAllWithAuthorEntityGraph();
}
