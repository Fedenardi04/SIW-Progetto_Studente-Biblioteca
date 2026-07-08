package it.uniroma3.siw.service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.model.Loan;
import it.uniroma3.siw.repository.BookRepository;
import it.uniroma3.siw.repository.LoanRepository;


@Service
public class LoanService {
    private LoanRepository loanRepository;
    private BookRepository bookRepository;

    public LoanService(LoanRepository loanRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
    }

    public List<Loan> findAllLoans() {
        return loanRepository.findAll();
    }

      public Optional<Loan> findLoanById(Long id) {
        return loanRepository.findById(id);
    }

    public List<Loan> findLoansByBookId(Long bookId) {
        return loanRepository.findByBookId(bookId);
    }

    public List<Loan> findActiveLoans() {
        return loanRepository.findByReturnedFalse();
    }
    
    public List<Loan> findReturnedLoans() {
        return loanRepository.findByReturnedTrue();
    }

     @Transactional
    public Loan saveLoan(Long bookId, Loan loan) {

        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));

        if (loanRepository.existsByBookIdAndReturnedFalse(bookId)) {
            throw new RuntimeException("This book is already on loan");
        }

        loan.setBook(book);
        loan.setStartDate(LocalDate.now());
        loan.setReturned(false);

        return loanRepository.save(loan);
    }

    @Transactional
    public void markAsReturned(Long id) {
        Loan loan = loanRepository.findById(id).orElseThrow(() -> new RuntimeException("Loan not found"));

        loan.setReturned(true);
        loanRepository.save(loan);
    }

    @Transactional
    public void deleteLoan(Long id) {
        loanRepository.deleteById(id);
    }
}

