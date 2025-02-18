CREATE TABLE IF NOT EXISTS catalog (
    id uuid PRIMARY KEY,
    movie_id VARCHAR(128) NOT NULL,
    title VARCHAR(128) NOT NULL,
    price_amount DECIMAL(5, 2) NOT NULL,
    price_currency VARCHAR(32) NOT NULL,
    rating_count INT NOT NULL,
    rating_avg DECIMAL(3, 2) NOT NULL);
CREATE INDEX catalog_movie_id ON catalog (movie_id);

CREATE OR REPLACE PROCEDURE add_catalog_entry(id uuid, title varchar, movie_id varchar, price_amount decimal, price_currency varchar, rating_avg decimal, rating_count integer)
LANGUAGE SQL
AS $$
INSERT INTO catalog VALUES(id, movie_id, title, price_amount, price_currency, rating_count, rating_avg) ON CONFLICT DO NOTHING
$$;

CREATE TABLE IF NOT EXISTS showtimes (
    id uuid PRIMARY KEY,
    movie_id VARCHAR(128) NOT NULL,
    movie_title VARCHAR(128) NOT NULL,
    date_start TIMESTAMP WITH TIME ZONE NOT NULL,
    date_end TIMESTAMP WITH TIME ZONE NOT NULL,
    price_amount DECIMAL(5, 2) NULL,
    price_currency VARCHAR(32) NULL);
CREATE INDEX showtimes_movie_id ON showtimes (movie_id);

CREATE TABLE IF NOT EXISTS ratings (
    username VARCHAR(128),
    catalog_entry_id uuid,
    rating DECIMAL(3, 2) NOT NULL,
    CONSTRAINT pk_ratings PRIMARY KEY (username, catalog_entry_id));
CREATE INDEX ratings_catalog_entry_id ON ratings (catalog_entry_id);

