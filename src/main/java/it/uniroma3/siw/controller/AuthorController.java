package it.uniroma3.siw.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.service.AuthorService;

@Controller
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
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
    public String save(@ModelAttribute("author") Author author) {
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
    public String deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return "redirect:/admin/authors";
    }
}