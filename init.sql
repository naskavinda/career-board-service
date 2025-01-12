CREATE SCHEMA IF NOT EXISTS career_board_schema;

INSERT INTO career_board_schema.user_account (user_id, active, created_at, first_name, last_name, "password", "role", username)
    VALUES
      (1, true, '2025-01-13 14:30:22.123', 'Jane', 'Smith', '$2a$10$dF3xC8Kj9X8zL5tY7mR2O.3jH1z6QXu9t5vH1QZ3Xz8nX5Y4Z6W8q', 'USER', 'janesmith'),
      (2, true, '2025-01-14 09:15:45.789', 'Bob', 'Johnson', '$2a$10$hG7bK9pL2mN3qR5fS4tX1.7vJ8Z2xY3X6tN9rC2wQ3wQ4yR5tH6Tm', 'ADMIN', 'bobjohnson'),
      (3, false, '2025-01-15 18:20:33.456', 'Alice', 'Williams', '$2a$10$kL8mP9nJ4X7bR2fT5sD3h.2qN5zG7tK8vH9sM3tN5rN7wQ8zL6T9m', 'USER', 'alicewilliams'),
      (4, true, '2025-01-16 11:05:17.890', 'Charlie', 'Brown', '$2a$10$pN7mR2wQ3xY3X6tN9rC2w.7vJ8Z2xY3X6tN9rC2wQ3wQ4yR5tH6Tm', 'USER', 'charliebrown'),
      (5,true,'2025-01-12 03:54:11.051','John','Doe','$2a$10$cXN.wnxQ8U.v4V1JDEaiSeEFmmg6u3Py4BemvnBPi1Qq9390r95WG','USER','johndoe');

INSERT INTO career_board_schema.user_post (post_id, "content", created_at, title, user_id)
  VALUES
    (1, 'Just landed my dream job in tech!', '2023-06-15 09:30:00', 'Career Success Story', 3),
    (2, 'Tips for acing your next coding interview', '2023-06-16 14:45:00', 'Interview Prep', 2),
    (3, 'The importance of networking in your job search', '2023-06-17 11:20:00', 'Networking Advice', 4),
    (4, 'My experience transitioning from finance to tech', '2023-06-18 16:55:00', 'Career Change', 1),
    (5, 'How to build a standout GitHub portfolio', '2023-06-19 10:10:00', 'Portfolio Tips', 5),
    (6, 'The pros and cons of remote work in tech', '2023-06-20 13:40:00', 'Remote Work Discussion', 2),
    (7, 'My journey learning machine learning', '2023-06-21 15:15:00', 'Learning ML', 3),
    (8, 'How to negotiate your salary in tech', '2023-06-22 09:00:00', 'Salary Negotiation', 4),
    (9, 'The importance of soft skills in tech careers', '2023-06-23 11:30:00', 'Soft Skills', 1),
    (10, 'My first month as a junior developer', '2023-06-24 14:20:00', 'New Dev Experience', 5);