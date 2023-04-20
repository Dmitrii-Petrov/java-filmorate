create table if not exists FILMS
(
    ID           BIGINT auto_increment
        primary key,
    NAME         CHARACTER VARYING(20) not null,
    DESCRIPTION  CHARACTER LARGE OBJECT(200),
    RELEASE_DATE TIMESTAMP             not null,
    DURATION     INTEGER               not null,
    RATING       CHARACTER VARYING(6)  not null
);
ALTER TABLE FILMS ALTER COLUMN ID RESTART WITH 1;

create table if not exists GENRE
(
    ID   BIGINT auto_increment
        primary key,
    NAME CHARACTER VARYING(20)
);

create table if not exists FILM_GENRE
(
    FILM_ID  BIGINT not null
        references FILMS,
    GENRE_ID BIGINT not null
        references GENRE,
    primary key (FILM_ID, GENRE_ID)
);

create table if not exists USERS
(
    ID       BIGINT auto_increment
        primary key,
    EMAIL    CHARACTER VARYING(20) not null,
    LOGIN    CHARACTER VARYING(20) not null,
    NAME     CHARACTER VARYING(20) not null,
    BIRTHDAY TIMESTAMP             not null
);
ALTER TABLE USERS ALTER COLUMN ID RESTART WITH 1;

create table if not exists FILM_LIKES
(
    FILM_ID BIGINT not null
        references FILMS,
    USER_ID BIGINT not null
        references USERS,
    primary key (FILM_ID, USER_ID)
);

create table if not exists FRIENDS
(
    USER_ID   BIGINT  not null
        references USERS,
    FRIEND_ID BIGINT  not null
        references USERS,
    MUTUAL    BOOLEAN not null,
    primary key (USER_ID, FRIEND_ID)
);

