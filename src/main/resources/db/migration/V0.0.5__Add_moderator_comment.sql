
-- V0.0.5__add_moderator_comment_to_user_post.sql

ALTER TABLE user_post
ADD COLUMN moderator_comment VARCHAR(255);