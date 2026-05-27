package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.siw.uniroma3.it.exception.DuplicateBookException;
import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.repository.BookRepository;
import jakarta.transaction.Transactional;

@Service
public class BookService {

	private BookRepository bookRepository;
	
	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}


	public Book findBookById(Long id) {
		return bookRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Book not found"));	
	}
	
	public List<Book> findAllBooks() {
        return bookRepository.findAllWithAuthor();
    }
	
	@Transactional
	public Book saveBook(Book book) {
		boolean duplicate = book.getId() == null
	            ? bookRepository.existsByTitleAndYear(book.getTitle(), book.getYear())
	            : bookRepository.existsByTitleAndYearAndIdNot(book.getTitle(), book.getYear(), book.getId());
	        if (duplicate) {
	            throw new DuplicateBookException(book.getTitle(), book.getYear());
	        }
	        return bookRepository.save(book);   
		    
	}
	
	@Transactional
	public void deleteBook(Long id) {
		Book book = findBookById(id);
        bookRepository.delete(book);
    }

	
}
