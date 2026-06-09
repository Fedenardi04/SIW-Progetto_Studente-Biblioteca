insert into book (id, title, year, url_image) values(nextval('book_seq'), 'Dune', 1965, '/images/dune.png');
insert into book (id, title, year, url_image) values(nextval('book_seq'), '1984', 1949, '/images/1984.png');
insert into book (id, title, year, url_image) values(nextval('book_seq'), 'Il Signore degli Anelli', 1954, '/images/lotr.png');
insert into book (id, title, year, url_image) values(nextval('book_seq'), 'Harry Potter e la Pietra Filosofale', 1997, '/images/hp1.png');
insert into book (id, title, year, url_image) values(nextval('book_seq'), 'Neuromante', 1984, '/images/neuromante.png');

insert into author (id, name, surname, birth_date) values(nextval('author_seq'), 'Frank', 'Herbert', '1920-10-08');
insert into author (id, name, surname, birth_date) values(nextval('author_seq'), 'George', 'Orwell', '1903-06-25');
insert into author (id, name, surname, birth_date) values(nextval('author_seq'), 'William', 'Gibson', '1948-03-17');
insert into author (id, name, surname, birth_date) values(nextval('author_seq'), 'J.R.R.', 'Tolkien', '1892-01-03');
insert into author (id, name, surname, birth_date) values(nextval('author_seq'), 'J.K.', 'Rowling', '1965-07-31');