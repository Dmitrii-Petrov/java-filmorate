create table if not exists "films"
(
    "id"           BIGINT auto_increment
        primary key,
    "name"         CHARACTER VARYING    not null,
    "description"  CHARACTER LARGE OBJECT(200),
    "release_date" TIMESTAMP            not null,
    "duration"     INTEGER              not null,
    "rating"       CHARACTER VARYING(6) not null
);

create table if not exists "genre"
(
    "id"   BIGINT auto_increment
        primary key,
    "name" CHARACTER VARYING(20)
);

create table if not exists "film_genre"
(
    "film_id"  BIGINT not null
        references "films",
    "genre_id" BIGINT not null
        references "genre",
    primary key ("film_id", "genre_id")
);

create table if not exists "users"
(
    "id"       BIGINT auto_increment
        primary key,
    "email"    CHARACTER VARYING(20) not null,
    "login"    CHARACTER VARYING(20) not null,
    "name"     CHARACTER VARYING(20) not null,
    "birthday" TIMESTAMP             not null
);

create table if not exists "film_likes"
(
    "film_id" BIGINT not null
        references "films",
    "user_id" BIGINT not null
        references "users",
    primary key ("film_id", "user_id")
);

create table if not exists "friends"
(
    "user_id"           BIGINT  not null
        references "users",
    "friend_id"         BIGINT  not null
        references "users",
    "confirmed_friends" BOOLEAN not null,
    primary key ("user_id", "friend_id")
);


