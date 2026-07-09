package it.uniroma3.siw.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.service.BookService;
import it.uniroma3.siw.service.ReviewService;

@Controller
public class ReviewController {

	private ReviewService reviewService;
    private BookService bookService;

	
	public ReviewController(ReviewService reviewService, BookService bookService) {
		super();
		this.reviewService = reviewService;
		this.bookService = bookService;
	}

	
    @GetMapping("/books/{bookId}/reviews/new")
    public String createForm(@PathVariable Long bookId, Model model) {

        model.addAttribute("book", bookService.findBookById(bookId).get());
        model.addAttribute("review", new Review());

        return "reviews/form";
    }

    @PostMapping("/books/{bookId}/reviews")
    public String save(@PathVariable Long bookId,
                       @ModelAttribute Review review,
                       Principal principal) {

        reviewService.saveReview(bookId, review, principal.getName());
        return "redirect:/books/" + bookId;
    }
}