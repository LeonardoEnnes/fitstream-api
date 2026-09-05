CREATE TABLE meals (
                       id UUID PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       description TEXT,
                       consumed_at TIMESTAMP NOT NULL
);
