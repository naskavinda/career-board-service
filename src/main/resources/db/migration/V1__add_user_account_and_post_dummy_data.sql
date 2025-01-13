INSERT INTO user_account (username, first_name, password, last_name, role)
VALUES
('naskavinda', 'Supun', '$2a$10$8anbsGw9GRnUK/CjlbATt.U0W71XaGTKf676JBWDV7ivq9xguZbdC', 'Kavinda', 'USER'),
('alice_smith', 'Alice', '$2a$10$8anbsGw9GRnUK/CjlbATt.U0W71XaGTKf676JBWDV7ivq9xguZbdC', 'Smith', 'ADMIN'),
('bob_jones', 'Bob', '$2a$10$8anbsGw9GRnUK/CjlbATt.U0W71XaGTKf676JBWDV7ivq9xguZbdC', 'Jones', 'MODERATOR'),
('charlie_brown', 'Charlie', '$2a$10$8anbsGw9GRnUK/CjlbATt.U0W71XaGTKf676JBWDV7ivq9xguZbdC', 'Brown', 'USER'),
('emma_davis', 'Emma', '$2a$10$8anbsGw9GRnUK/CjlbATt.U0W71XaGTKf676JBWDV7ivq9xguZbdC', 'Davis', 'ADMIN'),
('lucas_miller', 'Lucas', '$2a$10$8anbsGw9GRnUK/CjlbATt.U0W71XaGTKf676JBWDV7ivq9xguZbdC', 'Miller', 'USER'),
('lily_johnson', 'Lily', '$2a$10$8anbsGw9GRnUK/CjlbATt.U0W71XaGTKf676JBWDV7ivq9xguZbdC', 'Johnson', 'MODERATOR'),
('noah_williams', 'Noah', '$2a$10$8anbsGw9GRnUK/CjlbATt.U0W71XaGTKf676JBWDV7ivq9xguZbdC', 'Williams', 'USER'),
('mia_lee', 'Mia', '$2a$10$8anbsGw9GRnUK/CjlbATt.U0W71XaGTKf676JBWDV7ivq9xguZbdC', 'Lee', 'ADMIN'),
('oliver_martin', 'Oliver', '$2a$10$8anbsGw9GRnUK/CjlbATt.U0W71XaGTKf676JBWDV7ivq9xguZbdC', 'Martin', 'USER');


INSERT INTO user_post (user_id, title, content)
VALUES
(1, 'Welcome Post', 'Welcome to our new blog platform!'),
(1, 'Feature Highlights', 'Here are the top features of our application.'),
(2, 'My First Post', 'Excited to share my thoughts with everyone!'),
(3, 'Travel Diaries', 'Exploring the beautiful landscapes of Sri Lanka.'),
(4, 'Tech Updates', 'Latest trends in technology for 2025.'),
(4, 'Spring Boot Guide', 'Comprehensive guide to Spring Boot for beginners.'),
(5, 'Health and Fitness Tips', 'Tips to stay fit and healthy this year.'),
(2, 'Learning Java', 'My journey of mastering Java programming language.'),
(3, 'Photography Tips', 'How to take stunning photos with a smartphone.'),
(1, 'Community Guidelines', 'Please follow our community guidelines.');
