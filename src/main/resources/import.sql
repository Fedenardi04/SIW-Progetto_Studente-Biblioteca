insert into author (id, name, surname, birth_date) values (1, 'Frank', 'Herbert', '1920-10-08');

insert into author (id, name, surname, birth_date) values (2, 'George', 'Orwell', '1903-06-25');

insert into book (id, title, year, url_image, author_id) values (1, 'Dune', 1965, '/images/dune.png', 1);

insert into book (id, title, year, url_image, author_id) values (2, '1984', 1949, '/images/1984.png', 2);




-- Utente

INSERT INTO users(id, name, surname, email) VALUES (nextval('users_seq'),'Federico','Nardi','federico@email.it');

INSERT INTO credentials(id, username, password, role, user_id) VALUES (nextval('credentials_seq'),'federico','$2a$12$CF6RM/sA9x4u3GQ7DlMhNeSd54HeIL0VT3Y0BTg070yqx4gQTIXS.','ADMIN',currval('users_seq'));