INSERT INTO users (id, username, password) VALUES
    (1, 'user', '$2a$10$fac6Bta9tAhBFKmUpL5KRO0h8TpBZ0BDFaDfZ03UgIi0dwD88LURu');

INSERT INTO roles (id, name) VALUES
    (1, 'ROLE_USER');

INSERT INTO users_roles (user_id, roles_id) VALUES
    (1, 1);