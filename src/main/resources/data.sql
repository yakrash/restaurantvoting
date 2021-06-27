INSERT INTO USERS (EMAIL, FIRST_NAME, LAST_NAME, PASSWORD)
VALUES ('user@gmail.com', 'User_First', 'User_Last', '{noop}password'),
       ('user2222@gmail.com', '2User_First2', '2User_Last2', '{noop}password'),
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
VALUES ('2021-06-27', 'Soup', 3, 1),
       ('2021-06-27', 'Steak', 10, 1),
       ('2021-06-27', 'Dessert', 7, 1);

INSERT INTO VOTES (RESTAURANT_ID, USER_ID)
VALUES (1, 1),
       (2, 2);