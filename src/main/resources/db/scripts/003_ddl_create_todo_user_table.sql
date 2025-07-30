create table todo_user (
    id SERIAL PRIMARY KEY,
    name varchar not null,
    login varchar not null unique,
    password varchar not null
);
