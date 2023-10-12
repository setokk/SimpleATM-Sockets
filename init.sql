\c atm;

CREATE TABLE IF NOT EXISTS bankuser(
                     id bigserial primary key,
                     username text unique not null,
                     password text not null,
                     balance double precision not null
);

INSERT INTO bankuser (username, password, balance)
SELECT 'user', 'user', 25000
WHERE NOT EXISTS (SELECT 1 FROM bankuser);