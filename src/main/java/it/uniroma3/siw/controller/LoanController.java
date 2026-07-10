package it.uniroma3.siw.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Loan;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.BookService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.LoanService;
import jakarta.validation.Valid;

@Controller
public class LoanController {

    private final LoanService loanService;
    private final BookService bookService;
    private final CredentialsService credentialsService;

    public LoanController(LoanService loanService,
                          BookService bookService,
                          CredentialsService credentialsService) {
        this.loanService = loanService;
        this.bookService = bookService;
        this.credentialsService = credentialsService;
    }

    /*
     * GESTIONE ADMIN
     */

    @GetMapping("/admin/loans")
    public String adminList(Model model) {
        model.addAttribute("activeLoans",loanService.findActiveLoans());

        model.addAttribute("returnedLoans",loanService.findReturnedLoans());

        return "admin/loans/list";
    }

    @PostMapping("/admin/loans/{id}/return")
    public String markAsReturned(@PathVariable Long id) {
        loanService.markAsReturned(id);
        return "redirect:/admin/loans";
    }

    @PostMapping("/admin/loans/{id}/delete")
    public String deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
        return "redirect:/admin/loans";
    }

    /*
     * PRESTITO UTENTE
     */

    @GetMapping("/books/{bookId}/loans/new")
    public String createForm(@PathVariable Long bookId,
                             Model model) {

        Optional<Book> optionalBook =
                bookService.findBookById(bookId);

        if (optionalBook.isEmpty()) {
            return "redirect:/books";
        }

        if (loanService.isBookCurrentlyLoaned(bookId)) {
            return "redirect:/books/" + bookId;
        }

        model.addAttribute("book", optionalBook.get());
        model.addAttribute("loan", new Loan());

        return "loans/form";
    }

    @PostMapping("/books/{bookId}/loans")
    public String save(@PathVariable Long bookId,
                       @Valid @ModelAttribute("loan") Loan loan,
                       BindingResult bindingResult,
                       Principal principal,
                       Model model) {

        Optional<Book> optionalBook =
                bookService.findBookById(bookId);

        if (optionalBook.isEmpty()) {
            return "redirect:/books";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("book", optionalBook.get());
            return "loans/form";
        }

        try {
            loanService.saveLoan(
                    bookId,
                    loan,
                    principal.getName()
            );

            return "redirect:/books/" + bookId;

        } catch (RuntimeException e) {
            model.addAttribute("book", optionalBook.get());
            model.addAttribute("errorMessage", e.getMessage());

            return "loans/form";
        }
    }

    /*
     * PRESTITI DEL SINGOLO UTENTE
     */

    @GetMapping("/loans/my")
    public String myLoans(Model model,
                          Principal principal) {

        Optional<Credentials> optionalCredentials =
                credentialsService.findByUsername(
                        principal.getName()
                );

        if (optionalCredentials.isEmpty()) {
            return "redirect:/";
        }

        User user = optionalCredentials.get().getUser();

        model.addAttribute(
                "activeLoans",
                loanService.findActiveLoansByUser(user)
        );

        model.addAttribute(
                "returnedLoans",
                loanService.findReturnedLoansByUser(user)
        );

        return "loans/my";
    }
}