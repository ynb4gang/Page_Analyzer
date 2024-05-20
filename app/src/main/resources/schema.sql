DROP TABLE IF EXISTS url_checks;
DROP TABLE IF EXISTS urls;

CREATE TABLE urls (
                      id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      created_at TIMESTAMP
);
CREATE TABLE url_checks (
                            id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                            url_id BIGINT REFERENCES urls (id),
                            status_code INTEGER,
                            h1 VARCHAR(255),
                            title VARCHAR(255),
                            description TEXT,
                            created_at TIMESTAMP
);