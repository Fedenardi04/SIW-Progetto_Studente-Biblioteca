package it.siw.uniroma3.it.exception;

public class DuplicateBookException extends RuntimeException {

	public DuplicateBookException(String title, Integer year) {
        super("Il libro '" + title + "' (" + year + ") è già presente nel sistema");
    }
}
