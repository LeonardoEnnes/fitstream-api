CREATE TABLE workouts (
                          id UUID PRIMARY KEY,
                          title VARCHAR(255) NOT NULL,
                          description TEXT,
                          completed BOOLEAN NOT NULL DEFAULT FALSE,
                          created_at TIMESTAMP NOT NULL
);
