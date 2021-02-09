CREATE TYPE conditions AS ENUM ('new', 'used', 'damaged');

CREATE TABLE ads
(
    ad_id         serial      NOT NULL PRIMARY KEY,
    user_id       integer     NOT NULL,
    year          smallint    NOT NULL,
    brand         varchar(30) NOT NULL,
    model         varchar(30) NOT NULL,
    engine_volume smallint,
    condition     conditions  NOT NULL,
    mileage       bigint,
    engine_power  integer,
    car_pictures  varchar(100),
    creation_time timestamp   NOT NULL,
    editing_time  timestamp   NOT NULL,
    FOREIGN KEY (user_id)
        REFERENCES users (user_id),
);

CREATE TABLE user_information
(
    phone_number_id serial             NOT NULL PRIMARY KEY,
    phone_number    varchar(13) UNIQUE NOT NULL,
    user_id         integer            NOT NULL,
    FOREIGN KEY (user_id)
        REFERENCES users (user_id),
);

CREATE TABLE users
(
    user_id  serial             NOT NULL PRIMARY KEY,
    username varchar(20) UNIQUE NOT NULL,
    email    varchar(30) UNIQUE NOT NULL,
    password varchar(20)        NOT NULL,
    name     varchar(30)        NOT NULL,
);

CREATE TABLE pictures
(
    picture_id        serial              NOT NULL PRIMARY KEY,
    picture_reference varchar(100) UNIQUE NOT NULL,
    ad_id             integer             NOT NULL,
    FOREIGN KEY (ad_id)
        REFERENCES ads (ad_id),
);