package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("""
           SELECT r
           FROM Review r
           LEFT JOIN FETCH r.user u
           LEFT JOIN FETCH u.credentials
           WHERE r.book.id = :bookId
           ORDER BY r.creationDate DESC, r.id DESC
           """)
    List<Review> findByBookIdWithUser(@Param("bookId") Long bookId);
}