package it.uniroma3.siw.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.repository.BookRepository;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.ReviewRepository;

@Service
public class ReviewService {

	private ReviewRepository reviewRepository;
    private BookRepository bookRepository;
	private CredentialsRepository credentialsRepository;

	
	public ReviewService(ReviewRepository reviewRepository, 
			BookRepository bookRepository, 
			CredentialsRepository credentialsRepository) {
		super();
		this.reviewRepository = reviewRepository;
		this.bookRepository = bookRepository;
	}

	
    public List<Review> findReviewsByBookId(Long bookId) {
        return reviewRepository.findByBookId(bookId);
    }

    @Transactional
    public Review saveReview(Long bookId, Review review, String username) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Credentials credentials = credentialsRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        review.setBook(book);
        review.setUser(credentials.getUser());
        review.setCreationDate(LocalDate.now());

        return reviewRepository.save(review);
    }
}