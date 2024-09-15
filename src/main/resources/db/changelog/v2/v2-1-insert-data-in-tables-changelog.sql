INSERT INTO users (email, first_name, last_name, password)
VALUES ('Demo1@mail.com', 'Demo1_first', 'Demo1_last', '$2a$04$e3dR7Izwnw5iL8Wb28l79Ohb1jkFSFFUb8Hb4Pz3GdnF5fRAI4adS'),
       ('Demo2@mail.com', 'Demo2_first', 'Demo2_last', '$2a$04$e3dR7Izwnw5iL8Wb28l79Ohb1jkFSFFUb8Hb4Pz3GdnF5fRAI4adS'),
       ('Demo3@mail.com', 'Demo3_first', 'Demo3_last', '$2a$04$e3dR7Izwnw5iL8Wb28l79Ohb1jkFSFFUb8Hb4Pz3GdnF5fRAI4adS'),
       ('Demo4@mail.com', 'Demo4_first', 'Demo4_last', '$2a$04$e3dR7Izwnw5iL8Wb28l79Ohb1jkFSFFUb8Hb4Pz3GdnF5fRAI4adS'),
       ('Demo5@mail.com', 'Demo5_first', 'Demo5_last', '$2a$04$e3dR7Izwnw5iL8Wb28l79Ohb1jkFSFFUb8Hb4Pz3GdnF5fRAI4adS');

INSERT INTO tasks (priority, status, text, author, executor)
VALUES ('LOW', 'WAITING', 'Do the demo task 1', 1, NULL),
       ('MEDIUM', 'WAITING', 'Do the demo task 2', 1, NULL),
       ('LOW', 'IN_PROGRESS', 'Do the demo task 3', 1, 2),
       ('LOW', 'IN_PROGRESS', 'Do the demo task 4', 2, 3),
       ('HIGH', 'WAITING', 'Do the demo task 5', 2, NULL),
       ('MEDIUM', 'COMPLETED', 'Do the demo task 6', 3, 1),
       ('LOW', 'COMPLETED', 'Do the demo task 7', 3, 4),
       ('LOW', 'IN_PROGRESS', 'Do the demo task 8', 4, 3),
       ('HIGH', 'IN_PROGRESS', 'Do the demo task 9', 4, 5),
       ('MEDIUM', 'COMPLETED', 'Do the demo task 10', 5, 2);

INSERT INTO comments (text, author, task)
VALUES ('Text of 1st comment', 1, 3),
       ('Text of 2nd comment', 1, 6),
       ('Text of 3rd comment', 2, 1),
       ('Text of 4th comment', 2, 8),
       ('Text of 5th comment', 3, 6),
       ('Text of 6th comment', 3, 7),
       ('Text of 7th comment', 4, 6),
       ('Text of 8th comment', 4, 10),
       ('Text of 9th comment', 4, 2),
       ('Text of 10th comment', 5, 7);