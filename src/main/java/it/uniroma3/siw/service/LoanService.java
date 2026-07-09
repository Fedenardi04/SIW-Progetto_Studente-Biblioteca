package it.uniroma3.siw.service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Loan;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.BookRepository;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.LoanRepository;


@Service
public class LoanService {
	private LoanRepository loanRepository;
	private BookRepository bookRepository;
	private CredentialsRepository credentialsRepository;

	public LoanService(LoanRepository loanRepository,
			BookRepository bookRepository,
			CredentialsRepository credentialsRepository) {
		this.loanRepository = loanRepository;
		this.bookRepository = bookRepository;
		this.credentialsRepository = credentialsRepository;
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

	public List<Loan> findActiveLoansByUser(User user) {
		return loanRepository.findByUserIdAndReturnedFalse(user.getId());
	}

	public List<Loan> findReturnedLoansByUser(User user) {
		return loanRepository.findByUserIdAndReturnedTrue(user.getId());
	}

	public boolean isBookCurrentlyLoaned(Long bookId) {
		return loanRepository.existsByBookIdAndReturnedFalse(bookId);
	}

	@Transactional
	public Loan saveLoan(Long bookId, Loan loan, String username) {

	    if (loanRepository.existsByBookIdAndReturnedFalse(bookId)) {
	        throw new RuntimeException("Libro già in prestito");
	    }

	    Book book = bookRepository.findById(bookId)
	            .orElseThrow(() -> new RuntimeException("Libro non trovato"));

	    Credentials credentials = credentialsRepository.findByUsername(username)
	            .orElseThrow(() -> new RuntimeException("Utente non trovato"));

	    loan.setBook(book);
	    loan.setUser(credentials.getUser());
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

