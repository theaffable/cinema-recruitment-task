CREATE TABLE IF NOT EXISTS "catalog" (
    id uuid PRIMARY KEY,
    movie_id VARCHAR(128) NOT NULL,
    title VARCHAR(128) NOT NULL,
    price_amount DECIMAL(5, 2) NOT NULL,
    price_currency VARCHAR(32) NOT NULL,
    rating_count INT NOT NULL,
    rating_avg DECIMAL(3, 2) NOT NULL);
CREATE INDEX catalog_movie_id ON "catalog" (movie_id);

CREATE OR REPLACE PROCEDURE add_catalog_entry(id uuid, title varchar, movie_id varchar, price_amount decimal, price_currency varchar, rating_avg decimal, rating_count integer)
LANGUAGE SQL
AS $$
INSERT INTO catalog VALUES(id, title, movie_id, price_amount, price_currency, rating_count, rating_avg) ON CONFLICT DO NOTHING
$$;

