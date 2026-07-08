package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import it.uniroma3.siw.model.Loan;
import it.uniroma3.siw.service.BookService;
import it.uniroma3.siw.service.LoanService;

@Controller
public class LoanController {

    private LoanService loanService;
    private BookService bookService;

    public LoanController(LoanService loanService, BookService bookService) {
        this.loanService = loanService;
        this.bookService = bookService;
    }

    @GetMapping("/loans")
    public String list(Model model) {
        model.addAttribute("loans", loanService.findAllLoans());
        return "loans/list";
    }

    @GetMapping("/loans/active")
    public String activeLoans(Model model) {
        model.addAttribute("loans", loanService.findActiveLoans());
        return "loans/list";
    }

    @GetMapping("/books/{bookId}/loans/new")
    public String createForm(@PathVariable Long bookId, Model model) {

        model.addAttribute("book", bookService.findBookById(bookId).get());
        model.addAttribute("loan", new Loan());

        return "loans/form";
    }

    @PostMapping("/books/{bookId}/loans")
    public String save(@PathVariable Long bookId,
                       @ModelAttribute Loan loan,
                       Model model) {

        try {
            loanService.saveLoan(bookId, loan);
            return "redirect:/books/" + bookId;
        } catch (RuntimeException e) {
            model.addAttribute("book", bookService.findBookById(bookId).get());
            model.addAttribute("loan", loan);
            model.addAttribute("errorMessage", e.getMessage());
            return "loans/form";
        }
    }

    @GetMapping("/loans/return/{id}")
    public String markAsReturned(@PathVariable Long id) {
        loanService.markAsReturned(id);
        return "redirect:/loans";
    }

    @GetMapping("/loans/delete/{id}")
    public String deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
        return "redirect:/loans";
    }
}