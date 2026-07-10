package it.uniroma3.siw.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.dto.CreateReviewDTO;
import it.uniroma3.siw.dto.ReviewDTO;
import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.repository.BookRepository;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.ReviewRepository;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final CredentialsRepository credentialsRepository;

    public ReviewService(
            ReviewRepository reviewRepository,
            BookRepository bookRepository,
            CredentialsRepository credentialsRepository) {

        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
        this.credentialsRepository = credentialsRepository;
    }

    @Transactional
    public List<ReviewDTO> findReviewsByBookId(Long bookId) {
        return reviewRepository.findByBookIdWithUser(bookId)
                .stream()
                .map(ReviewDTO::new)
                .toList();
    }

    @Transactional
    public ReviewDTO saveReview(
            Long bookId,
            CreateReviewDTO reviewDTO,
            String username) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() ->
                        new RuntimeException("Libro non trovato"));

        Credentials credentials =
                credentialsRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("Utente non trovato"));

        Review review = new Review();
        review.setText(reviewDTO.getText());
        review.setCreationDate(LocalDate.now());
        review.setBook(book);
        review.setUser(credentials.getUser());

        Review savedReview = reviewRepository.save(review);

        return new ReviewDTO(savedReview);
    }
}