DROP TABLE IF EXISTS ads, users, user_information, user_phones, pictures;
DROP TYPE IF EXISTS conditions;

CREATE TYPE conditions AS ENUM ('new', 'used', 'damaged');

CREATE TABLE users
(
    user_id  bigserial          NOT NULL PRIMARY KEY,
    username varchar(20) UNIQUE NOT NULL,
    email    varchar(30) UNIQUE NOT NULL,
    password varchar(20)        NOT NULL
);

CREATE TABLE user_information
(
    user_id bigint      NOT NULL PRIMARY KEY,
    name    varchar(30) NOT NULL,
    FOREIGN KEY (user_id)
        REFERENCES users (user_id)
        ON DELETE CASCADE
);

CREATE TABLE user_phones
(
    phone_number_id bigserial          NOT NULL PRIMARY KEY,
    phone_number    varchar(13) UNIQUE NOT NULL,
    user_id         bigint             NOT NULL,
    FOREIGN KEY (user_id)
        REFERENCES users (user_id)
        ON DELETE CASCADE
);

CREATE TABLE ads
(
    ad_id         bigserial   NOT NULL PRIMARY KEY,
    user_id       bigint      NOT NULL,
    year          smallint    NOT NULL,
    brand         varchar(30) NOT NULL,
    model         varchar(30) NOT NULL,
    engine_volume smallint,
    condition     conditions  NOT NULL,
    mileage       bigint,
    engine_power  integer,
    creation_time timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    editing_time  timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id)
        REFERENCES users (user_id)
        ON DELETE CASCADE
);

CREATE TABLE pictures
(
    picture_id bigserial    NOT NULL PRIMARY KEY,
    reference  varchar(100) NOT NULL,
    ad_id      bigint       NOT NULL,
    FOREIGN KEY (ad_id)
        REFERENCES ads (ad_id)
        ON DELETE CASCADE
);

-- INSERT INTO users
-- VALUES (1, 'ed', 'ed@mail.ru', 123);
--
-- INSERT INTO user_information
-- VALUES (1, 'Eduard');
--
-- INSERT INTO user_phones
-- VALUES (1, +375291111111, 1),
--        (2, +375292222222, 1),
--        (3, +375293333333, 1);
--
-- INSERT INTO ads
-- VALUES (1, 1, 1999, 'ww', 'jj', 1400, 'new', 150000, 200, '2016-06-22 19:10:25-07', '2016-06-24 20:10:25-07'),
--        (2, 1, 1999, 'bb', 'aa', 1600, 'new', 150000, 200, '2016-06-22 19:10:25-07', '2016-06-24 20:10:25-07');
--
-- INSERT INTO pictures
-- VALUES (1, 'reference1', 1),
--        (2, 'reference2', 1);