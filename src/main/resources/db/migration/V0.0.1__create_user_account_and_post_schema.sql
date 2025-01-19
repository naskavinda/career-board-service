CREATE TABLE user_account (
    user_id BIGSERIAL PRIMARY KEY,                    -- Automatically generated unique ID (BIGSERIAL is similar to auto-increment)
    username VARCHAR(30) UNIQUE NOT NULL,              -- Username with a maximum length of 30 characters, unique, and not null
    first_name VARCHAR(30) NOT NULL,                   -- First name with a maximum length of 30 characters, not null
    password VARCHAR(64) NOT NULL,                     -- Password with a maximum length of 64 characters, not null
    last_name VARCHAR(30) NOT NULL,                    -- Last name with a maximum length of 30 characters, not null
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- Timestamp for creation (automatically sets to current time)
    active BOOLEAN NOT NULL DEFAULT TRUE,              -- Boolean flag for account active status (default is true)
    role VARCHAR(30) NOT NULL,                         -- Role with a maximum length of 30 characters, not null
    CONSTRAINT user_role_check CHECK (role IN ('ADMIN', 'USER', 'MODERATOR'))  -- Enum-like constraint for roles
);

CREATE TABLE user_post (
    post_id BIGSERIAL PRIMARY KEY,             -- Auto-generated unique ID for each post
    user_id BIGINT NOT NULL,                   -- Foreign key referencing the user_account table
    title VARCHAR(255) NOT NULL,               -- Title of the post, required
    content TEXT NOT NULL,                     -- Content of the post, required
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, -- Timestamp for when the post was created
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES user_account(user_id) ON DELETE CASCADE
);
