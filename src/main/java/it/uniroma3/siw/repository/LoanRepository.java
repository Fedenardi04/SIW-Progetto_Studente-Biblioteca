package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.model.Loan;

public interface LoanRepository extends JpaRepository<Loan,Long>{

    List<Loan> findByBookId(Long bookId);

    List<Loan> findByUserId(Long userId);
    
    List<Loan> findByUserIdAndReturnedFalse(Long userId);

    List<Loan> findByUserIdAndReturnedTrue(Long userId);

    List<Loan> findByReturnedFalse();
    
    List<Loan> findByReturnedTrue();

    boolean existsByBookIdAndReturnedFalse(Long bookId);
    
    boolean existsByBookId(Long bookId);

}

