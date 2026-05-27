package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.repository.BookRepository;

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
	
	public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
	
	public void deleteBook(Long id) {
		Book book = findBookById(id);
        bookRepository.delete(book);
    }

    public Book updateBook(Long id, Book updatedBook) {
        Book book = findBookById(id);

        book.setTitle(updatedBook.getTitle());
        book.setYear(updatedBook.getYear());
        book.setAuthor(updatedBook.getAuthor());

        return bookRepository.save(book);
    }
	
	
}
