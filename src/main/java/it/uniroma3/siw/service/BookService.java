package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.repository.BookRepository;
import jakarta.transaction.Transactional;

@Service
public class BookService {

	private BookRepository bookRepository;
	
	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}


	public Optional<Book> findBookById(Long id) {
		return bookRepository.findById(id);
	}
	
	public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }
	
	public boolean isDuplicate(Book book) {

	    if (book.getTitle() == null ||
	        book.getAuthor() == null ||
	        book.getAuthor().getId() == null) {

	        return false;
	    }

	    String normalizedTitle = book.getTitle().trim();

	    if (book.getId() == null) {
	        return bookRepository.existsByTitleIgnoreCaseAndAuthorId(normalizedTitle, book.getAuthor().getId());
	    }

	    return bookRepository.existsByTitleIgnoreCaseAndAuthorIdAndIdNot(normalizedTitle,book.getAuthor().getId(),book.getId());
	}

	
	@Transactional
	public Book saveBook(Book book) {
	    book.setTitle(book.getTitle().trim());
	    return bookRepository.save(book);
	}
	
	@Transactional
	public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
	
	
	
}
