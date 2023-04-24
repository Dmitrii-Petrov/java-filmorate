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

INSERT INTO MPA (RATING)
values ('G');
INSERT INTO MPA (RATING)
values ('PG');
INSERT INTO MPA (RATING)
values ('PG-13');
INSERT INTO MPA (RATING)
values ('R');
INSERT INTO MPA (RATING)
values ('NC-17');

INSERT INTO GENRE (NAME)
values ('Комедия');
INSERT INTO GENRE (NAME)
values ('Драма');
INSERT INTO GENRE (NAME)
values ('Мультфильм');
INSERT INTO GENRE (NAME)
values ('Триллер');
INSERT INTO GENRE (NAME)
values ('Документальный');
INSERT INTO GENRE (NAME)
values ('Боевик');