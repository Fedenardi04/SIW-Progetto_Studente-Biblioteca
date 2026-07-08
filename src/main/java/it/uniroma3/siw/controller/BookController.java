package it.uniroma3.siw.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.service.AuthorService;
import it.uniroma3.siw.service.BookService;
import it.uniroma3.siw.service.LoanService;

@Controller 
public class BookController {

	

	private BookService bookService;
	private AuthorService authorService;
	private LoanService loanService;

	public BookController(BookService bookService, AuthorService authorService, LoanService loanService) {
	    this.bookService = bookService;
	    this.authorService = authorService;
	    this.loanService = loanService;
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
	    model.addAttribute("bookAlreadyLoaned", loanService.isBookCurrentlyLoaned(id));
	    return "books/show";
	}
	
	@GetMapping("/books/new")
	public String createForm(Model model) {

	    model.addAttribute("book", new Book());
    model.addAttribute("authors", authorService.findAllAuthors());

	    return "books/form";
	}
	
	@PostMapping("/books")
    public String saveBook(@ModelAttribute("book") Book book,
                           Model model) {
		bookService.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/books/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Book book = bookService.findBookById(id).orElse(null);

        if (book == null) {
            return "redirect:/books";
        }

        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.findAllAuthors());
        return "books/form";
    }

    @GetMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
	
}
