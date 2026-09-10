CREATE TABLE supplements (
                             id UUID PRIMARY KEY,
                             name VARCHAR(255) NOT NULL,
                             dosage INT NOT NULL,
                             unit VARCHAR(50) NOT NULL,
                             taken_at TIMESTAMP NOT NULL
);
