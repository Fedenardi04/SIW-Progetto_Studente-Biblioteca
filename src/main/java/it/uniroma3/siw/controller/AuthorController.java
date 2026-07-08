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

	private AuthorService authorService;

	public AuthorController(AuthorService authorService) {
		this.authorService = authorService;
	}

	@GetMapping("/authors")
	public String list(Model model) {
	    model.addAttribute("authors", authorService.findAllAuthors());
	    return "authors/list";
	}
	
	@GetMapping("/authors/{id}")
	public String show(@PathVariable Long id, Model model) {

	    Optional<Author> author = authorService.findByIdWithBooks(id);

	    if(author.isEmpty())
	        return "redirect:/authors";

	    model.addAttribute("author", author.get());

	    return "authors/show";
	}
	
	@GetMapping("/authors/new")
	public String createForm(Model model) {
		
		model.addAttribute("author", new Author());
	    return "authors/form";
	}
	
	@PostMapping("/authors")
	public String save(@ModelAttribute Author author,
	                         Model model) {

		authorService.saveAuthor(author);
        return "redirect:/authors";
	}
	
	@GetMapping("/authors/delete/{id}")
	public String deleteAuthor(@PathVariable Long id) {
	    authorService.deleteAuthor(id);
	    return "redirect:/authors";
	}
}
