package it.uniroma3.siw.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import it.uniroma3.siw.dto.CreateReviewDTO;
import it.uniroma3.siw.dto.ReviewDTO;
import it.uniroma3.siw.service.ReviewService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books/{bookId}/reviews")
public class ReviewRestController {

    private final ReviewService reviewService;

    public ReviewRestController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<ReviewDTO> getReviews(
            @PathVariable Long bookId) {

        return reviewService.findReviewsByBookId(bookId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewDTO createReview(
            @PathVariable Long bookId,
            @Valid @RequestBody CreateReviewDTO reviewDTO,
            Principal principal) {

        return reviewService.saveReview(
                bookId,
                reviewDTO,
                principal.getName()
        );
    }
}