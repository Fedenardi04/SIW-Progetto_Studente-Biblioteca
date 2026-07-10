package it.uniroma3.siw.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Loan;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.BookService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.LoanService;
import jakarta.validation.Valid;

@Controller
public class LoanController {

    private LoanService loanService;
    private BookService bookService;
	private CredentialsService credentialsService;

    public LoanController(LoanService loanService, BookService bookService, CredentialsService credentialsService) {
        this.loanService = loanService;
        this.bookService = bookService;
        this.credentialsService = credentialsService;
    }

    @GetMapping("/admin/loans")
    public String list(Model model) {
    	model.addAttribute("activeLoans", loanService.findActiveLoans());
        model.addAttribute("returnedLoans", loanService.findReturnedLoans());
        return "admin/loans/list";
    }


    @GetMapping("/books/{bookId}/loans/new")
    public String createForm(@PathVariable Long bookId, Model model) {

        model.addAttribute("book", bookService.findBookById(bookId).get());
        model.addAttribute("loan", new Loan());

        return "loans/form";
    }
    
    @GetMapping("/loans/my")
    public String myLoans(Model model, Principal principal) {

        Credentials credentials = credentialsService.findByUsername(principal.getName()).get();
        User user = credentials.getUser();

        model.addAttribute("activeLoans", loanService.findActiveLoansByUser(user));
        model.addAttribute("returnedLoans", loanService.findReturnedLoansByUser(user));

        return "loans/my";
    }

    @PostMapping("/books/{bookId}/loans")
    public String save(@PathVariable Long bookId,
                       @Valid @ModelAttribute("loan") Loan loan,
                       BindingResult bindingResult,
                       Principal principal,
                       Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("book", bookService.findBookById(bookId).orElse(null));

            return "loans/form";
        }

        try {
            loanService.saveLoan(bookId, loan, principal.getName());

            return "redirect:/books/" + bookId;

        } catch (RuntimeException e) {
            model.addAttribute("book",bookService.findBookById(bookId).orElse(null));

            model.addAttribute("errorMessage",e.getMessage());

            return "loans/form";
        }
    }

    @PostMapping("/loans/{id}/return")
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