package it.siw.uniroma3.it.exception;

public class DuplicateAuthorException extends RuntimeException {

	public DuplicateAuthorException(String name, String surname) {
        super("Autore già esistente: " + name + " " + surname);
    }
}
