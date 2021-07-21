INSERT INTO USERS (EMAIL, FIRST_NAME, LAST_NAME, PASSWORD)
VALUES ('user@gmail.com', 'User_First', 'User_Last', '{noop}password'),
       ('user2@gmail.com', '2User_First2', '2User_Last2', '{noop}password'),
       ('admin@bzz.su', 'Admin_First', 'Admin_Last', '{noop}admin');

INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('USER', 1),
       ('USER', 2),
       ('ADMIN', 3),
       ('USER', 3);

INSERT INTO RESTAURANTS (NAME)
VALUES ('Milk&Meat'),
       ('BBQ Ribs');

INSERT INTO DISHES (DATE, NAME, PRICE_IN_DOLLARS, RESTAURANT_ID)
VALUES ('2021-07-21', 'Soup', 5, 1),
       ('2021-07-21', 'Steak', 10, 1),
       ('2021-07-21', 'Dessert', 12, 1),
       ('2021-07-10', 'Dessert', 12, 1),
       ('2021-07-21', 'Pizza', 15, 2);

INSERT INTO VOTES (RESTAURANT_ID, USER_ID, DATE)
VALUES (1, 1, '2021-07-15'),
       (1, 2, '2021-07-21'),
       (2, 3, '2021-07-21'),
       (2, 2, '2021-07-10');