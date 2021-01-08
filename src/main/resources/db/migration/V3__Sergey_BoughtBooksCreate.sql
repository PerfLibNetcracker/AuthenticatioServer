create table bought_books
(
    book_id  bigserial primary key,
    name     varchar(255),
    price    bigint
);

create table users_bought_books
(
    user_id         bigint not null references users (id),
    bought_books_id bigint not null references bought_books (book_id),
    primary key (user_id, bought_books_id)
);