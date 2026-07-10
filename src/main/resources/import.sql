INSERT INTO author(id, name, surname, birth_date) VALUES (nextval('author_seq'), 'George', 'Orwell', '1903-06-25');
INSERT INTO author(id, name, surname, birth_date) VALUES (nextval('author_seq'), 'J. R. R.', 'Tolkien', '1892-01-03');
INSERT INTO author(id, name, surname, birth_date) VALUES (nextval('author_seq'), 'Frank', 'Herbert', '1920-10-08');
INSERT INTO author(id, name, surname, birth_date) VALUES (nextval('author_seq'), 'Franz', 'Kafka', '1883-07-03');
INSERT INTO author(id, name, surname, birth_date) VALUES (nextval('author_seq'), 'Jane', 'Austen', '1775-12-16');
INSERT INTO author(id, name, surname, birth_date) VALUES (nextval('author_seq'), 'Fëdor', 'Dostoevskij', '1821-11-11');

INSERT INTO book(id, title, year, url_image, author_id) VALUES (nextval('book_seq'), '1984', 1949, 'https://covers.openlibrary.org/b/isbn/9780451524935-L.jpg', (SELECT id FROM author WHERE name = 'George' AND surname = 'Orwell'));
INSERT INTO book(id, title, year, url_image, author_id) VALUES (nextval('book_seq'), 'La fattoria degli animali', 1945, 'https://covers.openlibrary.org/b/isbn/9780451526342-L.jpg', (SELECT id FROM author WHERE name = 'George' AND surname = 'Orwell'));
INSERT INTO book(id, title, year, url_image, author_id) VALUES (nextval('book_seq'), 'Lo Hobbit', 1937, 'https://covers.openlibrary.org/b/isbn/9780547928227-L.jpg', (SELECT id FROM author WHERE name = 'J. R. R.' AND surname = 'Tolkien'));
INSERT INTO book(id, title, year, url_image, author_id) VALUES (nextval('book_seq'), 'Il Signore degli Anelli', 1954, 'https://covers.openlibrary.org/b/isbn/9780618640157-L.jpg', (SELECT id FROM author WHERE name = 'J. R. R.' AND surname = 'Tolkien'));
INSERT INTO book(id, title, year, url_image, author_id) VALUES (nextval('book_seq'), 'Dune', 1965, 'https://covers.openlibrary.org/b/isbn/9780441172719-L.jpg', (SELECT id FROM author WHERE name = 'Frank' AND surname = 'Herbert'));
INSERT INTO book(id, title, year, url_image, author_id) VALUES (nextval('book_seq'), 'Il processo', 1925, 'https://covers.openlibrary.org/b/isbn/9780805209990-L.jpg', (SELECT id FROM author WHERE name = 'Franz' AND surname = 'Kafka'));
INSERT INTO book(id, title, year, url_image, author_id) VALUES (nextval('book_seq'), 'La metamorfosi', 1915, 'https://covers.openlibrary.org/b/isbn/9780553213690-L.jpg', (SELECT id FROM author WHERE name = 'Franz' AND surname = 'Kafka'));
INSERT INTO book(id, title, year, url_image, author_id) VALUES (nextval('book_seq'), 'Orgoglio e pregiudizio', 1813, 'https://covers.openlibrary.org/b/isbn/9780141439518-L.jpg', (SELECT id FROM author WHERE name = 'Jane' AND surname = 'Austen'));
INSERT INTO book(id, title, year, url_image, author_id) VALUES (nextval('book_seq'), 'Delitto e castigo', 1866, 'https://covers.openlibrary.org/b/isbn/9780140449136-L.jpg', (SELECT id FROM author WHERE name = 'Fëdor' AND surname = 'Dostoevskij'));
INSERT INTO book(id, title, year, url_image, author_id) VALUES (nextval('book_seq'), 'I fratelli Karamazov', 1880, 'https://covers.openlibrary.org/b/isbn/9780374528379-L.jpg', (SELECT id FROM author WHERE name = 'Fëdor' AND surname = 'Dostoevskij'));

INSERT INTO users(id, name, surname, email) VALUES (nextval('users_seq'), 'Federico', 'Nardi', 'federico.admin@email.it');
INSERT INTO users(id, name, surname, email) VALUES (nextval('users_seq'), 'Mario', 'Rossi', 'mario.rossi@email.it');
INSERT INTO users(id, name, surname, email) VALUES (nextval('users_seq'), 'Lucia', 'Bianchi', 'lucia.bianchi@email.it');

INSERT INTO credentials(id, username, password, role, user_id) VALUES (nextval('credentials_seq'), 'federico', '$2y$12$mF4EhcqYyNID2ctzCAzmxeMo8Ko91Mi22QA9YQF1SWOpaoNYGsxE2', 'ADMIN', (SELECT id FROM users WHERE email = 'federico.admin@email.it'));
INSERT INTO credentials(id, username, password, role, user_id) VALUES (nextval('credentials_seq'), 'mario', '$2y$12$hW8h7qQd30BjiVvqDcHgMeIa5m121dLi6ESynX20ZkAOTFsiS0GKC', 'DEFAULT', (SELECT id FROM users WHERE email = 'mario.rossi@email.it'));
INSERT INTO credentials(id, username, password, role, user_id) VALUES (nextval('credentials_seq'), 'lucia', '$2y$12$hW8h7qQd30BjiVvqDcHgMeIa5m121dLi6ESynX20ZkAOTFsiS0GKC', 'DEFAULT', (SELECT id FROM users WHERE email = 'lucia.bianchi@email.it'));

INSERT INTO loan(id, start_date, end_date, returned, book_id, user_id) VALUES (nextval('loan_seq'), CURRENT_DATE - 7, CURRENT_DATE + 23, false, (SELECT id FROM book WHERE title = 'Dune' AND year = 1965), (SELECT id FROM users WHERE email = 'mario.rossi@email.it'));
INSERT INTO loan(id, start_date, end_date, returned, book_id, user_id) VALUES (nextval('loan_seq'), CURRENT_DATE - 60, CURRENT_DATE - 30, true, (SELECT id FROM book WHERE title = '1984' AND year = 1949), (SELECT id FROM users WHERE email = 'mario.rossi@email.it'));
INSERT INTO loan(id, start_date, end_date, returned, book_id, user_id) VALUES (nextval('loan_seq'), CURRENT_DATE - 4, CURRENT_DATE + 26, false, (SELECT id FROM book WHERE title = 'Lo Hobbit' AND year = 1937), (SELECT id FROM users WHERE email = 'lucia.bianchi@email.it'));
INSERT INTO loan(id, start_date, end_date, returned, book_id, user_id) VALUES (nextval('loan_seq'), CURRENT_DATE - 90, CURRENT_DATE - 60, true, (SELECT id FROM book WHERE title = 'La metamorfosi' AND year = 1915), (SELECT id FROM users WHERE email = 'lucia.bianchi@email.it'));