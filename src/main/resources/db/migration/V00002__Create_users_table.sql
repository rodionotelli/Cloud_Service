create table users (
    id bigserial primary key,
    "password" varchar(255) NOT NULL,
    username varchar(255) NOT NULL
);

create table roles (
    id bigserial primary key,
    "name" varchar(255) NOT NULL UNIQUE
);

create table users_roles (
    user_id int8 NOT NULL,
    roles_id int8 NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (roles_id) REFERENCES roles(id)
);