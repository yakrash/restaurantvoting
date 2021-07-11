[![Codacy Badge](https://app.codacy.com/project/badge/Grade/9d2a6580b752440f9676825c57a1658b)](https://www.codacy.com/gh/yakrash/restaurantvoting/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=yakrash/restaurantvoting&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.com/yakrash/restaurantvoting.svg?branch=master)](https://travis-ci.com/yakrash/restaurantvoting)

Design and implement a REST API using Hibernate/Spring-Boot without frontend.

The task is:

Build a voting system for deciding where to have lunch.

    2 types of users: admin and regular users
    Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
    Menu changes each day (admins do the updates)
    Users can vote on which restaurant they want to have lunch at
    Only one vote counted per user
    If user votes again the same day:
        If it is before 11:00 we assume that he changed his mind.
        If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides a new menu each day.

get menu of restaurant with id=1 for today
curl -s http://localhost:8080/api/restaurant/1/menu --user user@gmail.com:password

get all menus for today
curl -s http://localhost:8080/api/restaurant/all-menu-today --user user@gmail.com:password

add menu with dishes
curl -s -i -X POST -d '[{"name":"New dish","priceInDollars":100},{"name": "Dish2","priceInDollars":300}]' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/api/restaurant/2/menu --user admin@gmail.com:admin

add menu with dish
curl -s -i -X POST -d '[{"name":"New dish only","priceInDollars":400}]' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/api/restaurant/2/menu --user admin@gmail.com:admin