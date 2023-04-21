create table if not exists GENRE
(
    ID   BIGINT auto_increment
        primary key,
    NAME CHARACTER VARYING(20)
);

create table if not exists MPA
(
    ID          INTEGER auto_increment,
    RATING      CHARACTER VARYING(10) not null,
    DESCRIPTION CHARACTER VARYING(100),
    constraint MPA_PK
        primary key (ID)
);

create table if not exists FILMS
(
    ID           BIGINT auto_increment
        primary key,
    NAME         CHARACTER VARYING(20) not null,
    DESCRIPTION  CHARACTER LARGE OBJECT(200),
    RELEASE_DATE TIMESTAMP             not null,
    DURATION     INTEGER               not null,
    RATING       INTEGER,
    constraint FILMS_MPA_ID_FK
        foreign key (RATING) references MPA
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
    USER_ID   BIGINT not null
        references USERS,
    FRIEND_ID BIGINT not null
        references USERS,
    primary key (USER_ID, FRIEND_ID)
);



delete
from FILM_LIKES;
delete
from FILM_GENRE;
delete
from FRIENDS;
delete
from FILMS;
delete
from USERS;
delete
from GENRE;
delete
from MPA;

INSERT INTO MPA (RATING) values ( 'G' );
INSERT INTO MPA (RATING) values ( 'PG' );
INSERT INTO MPA (RATING) values ( 'PG-13' );
INSERT INTO MPA (RATING) values ( 'R' );
INSERT INTO MPA (RATING) values ( 'NC-17' );

INSERT INTO GENRE (NAME) values ( 'Комедия' );
INSERT INTO GENRE (NAME) values ( 'Драма' );
INSERT INTO GENRE (NAME) values ( 'Мультфильм' );
INSERT INTO GENRE (NAME) values ( 'Триллер' );
INSERT INTO GENRE (NAME) values ( 'Документальный' );
INSERT INTO GENRE (NAME) values ( 'Боевик' );

ALTER TABLE FILMS ALTER COLUMN ID RESTART WITH 1;

ALTER TABLE USERS ALTER COLUMN ID RESTART WITH 1;