CREATE SCHEMA IF NOT EXISTS career_board_schema;

-- Create user_account table if it does not exist
CREATE TABLE IF NOT EXISTS user_account (
    userId BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(30) UNIQUE NOT NULL,
    first_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(30) NOT NULL,
    password VARCHAR(64) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    active BOOLEAN NOT NULL,
    role VARCHAR(20) NOT NULL
);
INSERT INTO user_account (username, first_name, last_name, password, created_at, active, role)
VALUES 
    ('john_doe', 'John', 'Doe', 'password123', NOW() - INTERVAL 10 DAY, TRUE, 'ADMIN'),
    ('jane_smith', 'Jane', 'Smith', 'password456', NOW() - INTERVAL 5 DAY, TRUE, 'USER'),
    ('alex_jones', 'Alex', 'Jones', 'password789', NOW() - INTERVAL 20 DAY, FALSE, 'ADMIN'),
    ('sarah_brown', 'Sarah', 'Brown', 'password101', NOW() - INTERVAL 15 DAY, TRUE, 'USER'),
    ('mike_jackson', 'Mike', 'Jackson', 'password112', NOW() - INTERVAL 10 DAY, TRUE, 'USER');


CREATE TABLE IF NOT EXISTS posts (
    postId BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES user_account(userId) ON DELETE CASCADE
);

INSERT INTO posts (title, content, created_at, user_id)
VALUES 
    ('First Post by john_doe', 'This is a dummy post content for user john_doe', (SELECT userId FROM user_account WHERE username = 'john_doe')),
    ('Second Post by john_doe', 'This is another dummy post content for user john_doe', (SELECT userId FROM user_account WHERE username = 'john_doe')),

    ('First Post by jane_smith', 'This is a dummy post content for user jane_smith', (SELECT userId FROM user_account WHERE username = 'jane_smith')),
    ('Second Post by jane_smith', 'This is another dummy post content for user jane_smith', (SELECT userId FROM user_account WHERE username = 'jane_smith')),

    ('First Post by alex_jones', 'This is a dummy post content for user alex_jones', (SELECT userId FROM user_account WHERE username = 'alex_jones')),
    ('Second Post by alex_jones', 'This is another dummy post content for user alex_jones', (SELECT userId FROM user_account WHERE username = 'alex_jones'));