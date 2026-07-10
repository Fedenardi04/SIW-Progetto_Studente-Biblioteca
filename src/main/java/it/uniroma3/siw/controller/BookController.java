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

    private final BookService bookService;
    private final AuthorService authorService;
    private final LoanService loanService;

    public BookController(BookService bookService,
                          AuthorService authorService,
                          LoanService loanService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.loanService = loanService;
    }

    /*
     * PAGINE PUBBLICHE
     */

    @GetMapping("/books")
    public String publicList(Model model) {
        model.addAttribute("books", bookService.findAllBooks());
        return "books/list";
    }

    @GetMapping("/books/{id}")
    public String publicShow(@PathVariable Long id, Model model) {

        Optional<Book> optionalBook = bookService.findBookById(id);

        if (optionalBook.isEmpty()) {
            return "redirect:/books";
        }

        model.addAttribute("book", optionalBook.get());
        model.addAttribute(
                "bookAlreadyLoaned",
                loanService.isBookCurrentlyLoaned(id)
        );

        return "books/show";
    }

    /*
     * PAGINE ADMIN
     */

    @GetMapping("/admin/books")
    public String adminList(Model model) {
        model.addAttribute("books", bookService.findAllBooks());
        return "admin/books/list";
    }
    
    @GetMapping("/admin/books/{id}")
    public String adminShow(@PathVariable Long id, Model model) {

        Optional<Book> optionalBook = bookService.findBookById(id);

        if (optionalBook.isEmpty()) {
            return "redirect:/admin/books";
        }

        model.addAttribute("book", optionalBook.get());
        model.addAttribute("bookAlreadyLoaned", loanService.isBookCurrentlyLoaned(id));

        return "admin/books/show";
    }

    @GetMapping("/admin/books/new")
    public String createForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.findAllAuthors());

        return "admin/books/form";
    }

    @PostMapping("/admin/books")
    public String saveBook(@ModelAttribute("book") Book book) {
        bookService.saveBook(book);
        return "redirect:/admin/books";
    }

    @GetMapping("/admin/books/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {

        Book book = bookService.findBookById(id).orElse(null);

        if (book == null) {
            return "redirect:/admin/books";
        }

        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.findAllAuthors());

        return "admin/books/form";
    }

    @PostMapping("/admin/books/{id}/delete")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/admin/books";
    }
}