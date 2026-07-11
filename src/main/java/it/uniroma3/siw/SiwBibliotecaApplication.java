package it.uniroma3.siw;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.repository.BookRepository;
import jakarta.persistence.EntityManager;

/* 
Testo tre strategie di fetch per il caricamento di una lista di 10 libri e 6 autori, ogni libro mostra l'autore, quindi
con il caricamento LAZY si verifica il problema N+1 query, il confronto è tra LAZY, JOIN FETCH ed EntityGraph, si eseguono 10 ripetizioni
del test, ignorando la prima perché di warm-up, e riporto la media dei tempi di ogni esecuzione
LAZY = 13,501 ms
JOIN FETCH = 4,606 ms 
EntityGraph = 5,914 ms
*/
@SpringBootApplication
public class SiwBibliotecaApplication implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final EntityManager entityManager;

    private static final int REPETITIONS = 10;
    private static final int WARM_UP = 1;

    public SiwBibliotecaApplication(BookRepository bookRepository,
                                    EntityManager entityManager) {
        this.bookRepository = bookRepository;
        this.entityManager = entityManager;
    }

    public static void main(String[] args) {
        SpringApplication.run(SiwBibliotecaApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) {

/*        System.out.println();
        System.out.println("===== ANALISI SPERIMENTALE =====");

        testBookListWithAuthor();

        System.out.println("===== FINE ANALISI =====");
        System.out.println();
*/
    }

    private void testBookListWithAuthor() {

        double totalTimeMilliseconds = 0;
        int numberOfBooks = 0;

        for (int i = 0; i < WARM_UP + REPETITIONS; i++) {

            /*
             * Svuota il persistence context, in modo che Hibernate
             * non riutilizzi entità caricate nell'esecuzione precedente.
             */
            entityManager.clear();

            long startTime = System.nanoTime();

            /*
             * ATTIVA UNA SOLA STRATEGIA ALLA VOLTA.
             */

            List<Book> books = bookRepository.findAll(); // LAZY

            // List<Book> books = bookRepository.findAllWithAuthor();	// JOIN FETCH

            // List<Book> books = bookRepository.findAllWithAuthorEntityGraph();

            /*
             * È necessario accedere realmente all'autore.
             * Altrimenti, con LAZY, Hibernate potrebbe non caricarlo.
             */
            for (Book book : books) {

                String authorData =
                        book.getAuthor().getName()
                        + " "
                        + book.getAuthor().getSurname();

                /*
                 * Evita che il risultato venga completamente ignorato.
                 */
                if (authorData.isBlank()) {
                    throw new IllegalStateException(
                            "Autore non valido"
                    );
                }
            }

            long endTime = System.nanoTime();

            double elapsedMilliseconds = (endTime - startTime) / 1_000_000.0;

            if (i < WARM_UP) {
                System.out.printf(
                        "Warm-up: %.3f ms%n",
                        elapsedMilliseconds
                );
            } else {
                totalTimeMilliseconds += elapsedMilliseconds;
                numberOfBooks = books.size();

                System.out.printf(
                        "Esecuzione %2d: %.3f ms%n",
                        i - WARM_UP + 1,
                        elapsedMilliseconds
                );
        }

        double averageTime = totalTimeMilliseconds / REPETITIONS;

        System.out.println();
        System.out.println("Numero libri: " + numberOfBooks);

        System.out.printf(
                "Tempo medio su %d esecuzioni: %.3f ms%n",
                REPETITIONS,
                averageTime
        );
    }
        
    }
    
}    
