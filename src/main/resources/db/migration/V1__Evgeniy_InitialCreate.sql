create table users(
	id bigserial primary key,
	username varchar(30) not null,
	password varchar(80),
	email varchar(50) UNIQUE
);

create table roles (
	id bigserial primary key,
	name varchar(50) not null
);

create table users_roles (
	user_id bigint not null references users(id),
	role_id bigint not null references roles(id),
	primary key(user_id, role_id)
);

insert into roles (name) values
	('ROLE_USER') , ('ROLE_AUTHOR');

insert into users (username, password, email) values
	('user', '$2y$12$fTpQ0qKXEA8JRNrDLwot6Ooy9Q5egCc4iYdUz2NPHySFBtNsjsOTS', 'user@gmail.com');

insert into users_roles (user_id, role_id) values
	(1, 2);

create table books_user(
    user_id bigint not null references users,
    book_id bigint not null references main_model.book,
    primary key(user_id, book_id)
);