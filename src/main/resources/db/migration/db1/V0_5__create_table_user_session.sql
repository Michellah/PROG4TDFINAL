CREATE TABLE user_session (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    session_id VARCHAR(255) UNIQUE NOT NULL,
    expiration_date TIMESTAMP NOT NULL,
    CONSTRAINT fk_user_session_user FOREIGN KEY (user_id) REFERENCES user_entity (id)
);
