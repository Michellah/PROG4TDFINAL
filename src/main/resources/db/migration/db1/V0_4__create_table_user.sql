CREATE TABLE user_entity (
    id SERIAL PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

INSERT INTO user_entity(id, user_name, password)
VALUES
(1, 'test', 'oojohc5Z');
