INSERT INTO users (email, first_name, last_name, password)
VALUES ('Demo1@mail.com', 'Demo1_first', 'Demo1_last',
        '$2a$04$e3dR7Izwnw5iL8Wb28l79Ohb1jkFSFFUb8Hb4Pz3GdnF5fRAI4adS'),
       ('Demo2@mail.com', 'Demo2_first', 'Demo2_last',
        '$2a$04$e3dR7Izwnw5iL8Wb28l79Ohb1jkFSFFUb8Hb4Pz3GdnF5fRAI4adS');

INSERT INTO tasks (priority, status, text, author, executor)
VALUES ('LOW', 'WAITING', 'Do the demo task 1', 1, NULL),
       ('MEDIUM', 'WAITING', 'Do the demo task 2', 1, NULL),
       ('LOW', 'IN_PROGRESS', 'Do the demo task 3', 1, 2),
       ('LOW', 'IN_PROGRESS', 'Do the demo task 4', 2, 1);


INSERT INTO comments (text, author, task)
VALUES ('Text of 1st comment', 1, 4),
       ('Text of 2nd comment', 1, 4),
       ('Text of 3rd comment', 2, 3),
       ('Text of 4th comment', 2, 3),
       ('Text of 5th comment', 2, 3);