package it.uniroma3.siw.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.service.AuthorService;
import it.uniroma3.siw.service.BookService;
import jakarta.validation.Valid;

@Controller
public class AuthorController {

	private final AuthorService authorService;
	private final BookService bookService;

	public AuthorController(AuthorService authorService, BookService bookService) {
	    this.authorService = authorService;
	    this.bookService = bookService;
	}

    /*
     * PAGINE PUBBLICHE
     */

    @GetMapping("/authors")
    public String publicList(Model model) {
        model.addAttribute("authors", authorService.findAllAuthors());
        return "authors/list";
    }

    @GetMapping("/authors/{id}")
    public String publicShow(@PathVariable Long id, Model model) {

        Optional<Author> optionalAuthor =
                authorService.findByIdWithBooks(id);

        if (optionalAuthor.isEmpty()) {
            return "redirect:/authors";
        }

        model.addAttribute("author", optionalAuthor.get());

        return "authors/show";
    }

    /*
     * PAGINE ADMIN
     */

    @GetMapping("/admin/authors")
    public String adminList(Model model) {
        model.addAttribute("authors", authorService.findAllAuthors());
        return "admin/authors/list";
    }
    
    @GetMapping("/admin/authors/{id}")
    public String adminShow(@PathVariable Long id, Model model) {

        Optional<Author> optionalAuthor =
                authorService.findByIdWithBooks(id);

        if (optionalAuthor.isEmpty()) {
            return "redirect:/admin/authors";
        }

        model.addAttribute("author", optionalAuthor.get());

        return "admin/authors/show";
    }

    @GetMapping("/admin/authors/new")
    public String createForm(Model model) {
        model.addAttribute("author", new Author());
        return "admin/authors/form";
    }

    @PostMapping("/admin/authors")
    public String save(@Valid @ModelAttribute("author") Author author, BindingResult bindingResult) {

        if (!bindingResult.hasFieldErrors("name") &&
            !bindingResult.hasFieldErrors("surname") &&
            !bindingResult.hasFieldErrors("birthDate") &&
            authorService.isDuplicate(author)) {

            bindingResult.reject(
                    "author.duplicate",
                    "Esiste già un autore con nome, cognome e data di nascita uguali"
            );
        }

        if (bindingResult.hasErrors()) {
            return "admin/authors/form";
        }

        authorService.saveAuthor(author);

        return "redirect:/admin/authors";
    }

    @GetMapping("/admin/authors/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {

        Author author = authorService.findAuthorById(id).orElse(null);

        if (author == null) {
            return "redirect:/admin/authors";
        }

        model.addAttribute("author", author);

        return "admin/authors/form";
    }

    @PostMapping("/admin/authors/{id}/delete")
    public String deleteAuthor(@PathVariable Long id,
                               RedirectAttributes redirectAttributes) {

        if (bookService.hasBooksByAuthor(id)) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "L'autore non può essere eliminato perché ha uno o più libri associati."
            );

            return "redirect:/admin/authors";
        }

        authorService.deleteAuthor(id);

        redirectAttributes.addFlashAttribute("successMessage", "Autore eliminato correttamente.");

        return "redirect:/admin/authors";
    }
}