CREATE TABLE post_image (
    image_id SERIAL PRIMARY KEY,
    post_id BIGINT NOT NULL,
    image_name VARCHAR(500) NOT NULL,
    CONSTRAINT fk_post
        FOREIGN KEY (post_id) 
        REFERENCES user_post(post_id)
        ON DELETE CASCADE
);
