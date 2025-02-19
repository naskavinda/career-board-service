-- First add the new column (nullable)
ALTER TABLE user_account ADD COLUMN IF NOT EXISTS current_company VARCHAR(30) NULL;

-- Add updatedAt column
ALTER TABLE user_account ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP;

-- Set active column default value to true
ALTER TABLE user_account ALTER COLUMN active SET DEFAULT TRUE;

-- Now remove the firstname and lastname columns after data migration
ALTER TABLE user_account DROP COLUMN IF EXISTS first_name;
ALTER TABLE user_account DROP COLUMN IF EXISTS last_name;

-- Remove NOT NULL constraint from current_company
ALTER TABLE user_account ALTER COLUMN current_company DROP NOT NULL;
