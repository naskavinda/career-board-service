INSERT INTO user_account (username, first_name, password, last_name, role)
VALUES
('user', 'Supun', '$2a$10$8anbsGw9GRnUK/CjlbATt.U0W71XaGTKf676JBWDV7ivq9xguZbdC', 'Kavinda', 'USER'),
('admin', 'Alice', '$2a$10$8anbsGw9GRnUK/CjlbATt.U0W71XaGTKf676JBWDV7ivq9xguZbdC', 'Smith', 'ADMIN'),
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
(1, 'Welcome Post', 'Welcome! We are thrilled to have you join our vibrant and ever-growing community. Whether you’re here to learn, share, or connect, this space is designed to inspire collaboration and meaningful interactions. Our mission is to create an environment where ideas flow freely, questions find answers, and everyone feels valued and supported. No matter where you’re from or what you bring to the table, you belong here, and we can’t wait to embark on this journey together.

What You Can Expect
    This community thrives on inclusivity, knowledge-sharing, and mutual respect. You’ll find resources, discussions, and opportunities tailored to help you grow, whether you’re a beginner or an expert in your field. We encourage you to explore our content, participate in conversations, and ask questions—because every voice matters. Our team is always here to guide you, and our members are eager to lend a helping hand. Together, we’ll turn challenges into opportunities and ideas into achievements.

Let’s Connect and Grow
    As you settle in, take a moment to introduce yourself and share what brought you here. Your unique story and perspective are what make this community special. Dive into the conversations, spark discussions, and don’t hesitate to reach out if you need assistance or just want to connect. We’re all here to learn, grow, and support each other. So once again, welcome aboard—we’re excited to see what we can accomplish together!'),
(1, 'Feature Highlights', 'Here are the top features of our application.'),
(2, 'My First Post', 'Excited to share my thoughts with everyone!'),
(3, 'Travel Diaries', 'Exploring the beautiful landscapes of Sri Lanka.'),
(4, 'Tech Updates', 'Latest trends in technology for 2025.'),
(4, 'Spring Boot Guide', 'Comprehensive guide to Spring Boot for beginners.'),
(5, 'Health and Fitness Tips', 'Tips to stay fit and healthy this year.'),
(2, 'Learning Java', 'My journey of mastering Java programming language.'),
(3, 'Photography Tips', 'How to take stunning photos with a smartphone.'),
(1, 'Community Guidelines', 'Please follow our community guidelines.');
