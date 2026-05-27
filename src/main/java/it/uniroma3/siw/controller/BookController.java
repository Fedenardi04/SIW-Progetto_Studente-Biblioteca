package it.uniroma3.siw.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.service.BookService;

@Controller 
public class BookController {

	private BookService bookService;
	
	
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}
	
	@GetMapping("/books")
	public String list(Model model) {
		model.addAttribute("books", bookService.findAllBooks());
		return "books/list";
	}
	
	@GetMapping("/books/{id}")
	public String show(@PathVariable Long id, Model model) {

	    Optional<Book> optional = bookService.findBookById(id);

	    if (optional.isEmpty()) {
	        return "redirect:/books";
	    }

	    model.addAttribute("book", optional.get());
	    return "books/show";
	}
	
	@GetMapping("/books/new")
	public String createForm(Model model) {

	    model.addAttribute("book", new Book());
//	    model.addAttribute("authors", authorService.findAll());

	    return "books/form";
	}
	
}
